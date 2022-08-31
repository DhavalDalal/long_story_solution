package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Tag;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class PortfolioSpecs {
  private final String portfolioId = "123";
  private final Stock apple = new Stock("Apple Inc.", "AAPL");
  private final Stock google = new Stock("Google Inc.", "GOOG");
  private final Date now = new Date();
  private final Holding appleHolding = new Holding(now, apple, 10, 25.56);
  private final Holding googleHolding = new Holding(now, google, 10, 35.56);

  @Mock
  private NationalStockService nationalStockService;

  @Test
  public void emptyPortfolioHasNoId() {
    final Portfolio portfolio = new Portfolio();
    assertThat(portfolio.id, is(nullValue()));
  }

  @Test
  public void updateIdIfPortfolioHasNoId() {
    // Given
    final Portfolio portfolio = new Portfolio() {
      @Override
      String createId() {
        return portfolioId;
      }
    };
    // When
    final Portfolio updatedPortfolio = portfolio.updateIdIfEmpty();
    // Then
    assertThat(updatedPortfolio.id, is(portfolioId));
  }

  @Test
  public void emptyPortfolioHasZeroNetWorth() {
    final Portfolio portfolio = new Portfolio();
    assertThat(portfolio.networth(), is(0d));
  }

  @Test
  public void netWorth() {
    final Portfolio portfolio = new Portfolio(portfolioId, List.of(appleHolding, googleHolding));
    final double portfolioNetWorth = appleHolding.networth() + googleHolding.networth();
    assertThat(portfolio.networth(), is(portfolioNetWorth));
  }

  @Test
  public void updateNetWorthBasedOnLatestPrices() throws Exception {
    // Given
    final ArrayList<Holding> holdings = new ArrayList<>() {{
      add(googleHolding);
      add(appleHolding);
    }};
    final Portfolio portfolio = new Portfolio(portfolioId, holdings);
    final double newGooglePrice = 37.2;
    given(nationalStockService.getPrice(google.code)).willReturn(newGooglePrice);
    final double newApplePrice = 27.2;
    given(nationalStockService.getPrice(apple.code)).willReturn(newApplePrice);
    // When
    final Portfolio updatedPortfolio = portfolio.track(nationalStockService);
    // Then
    Double newNetWorth = (googleHolding.quantity * newGooglePrice) + (appleHolding.quantity * newApplePrice);
    assertThat(updatedPortfolio.networth(), is(newNetWorth));
  }

  @Test
  public void leavesNetWorthUnchangedWhenUnableToReachForTheLatestPrices() throws Exception {
    // Given
    final ArrayList<Holding> holdings = new ArrayList<>() {{
      add(googleHolding);
      add(appleHolding);
    }};
    final Portfolio portfolio = new Portfolio(portfolioId, holdings);
    given(nationalStockService.getPrice(google.code)).willThrow(new Exception("Could Not Connect to Nation Stock Exchange!"));
    given(nationalStockService.getPrice(apple.code)).willThrow(new Exception("Could Not Connect to Nation Stock Exchange!"));
    // When
    final Portfolio updatedPortfolio = portfolio.track(nationalStockService);
    // Then
    Double oldNetWorth = (googleHolding.quantity * googleHolding.lastPrice) + (appleHolding.quantity * appleHolding.lastPrice);
    assertThat(updatedPortfolio.networth(), is(oldNetWorth));
  }

  @Test
  public void updatesNetWorthForAvailableLatestPrices() throws Exception {
    // Given
    final ArrayList<Holding> holdings = new ArrayList<>() {{
      add(googleHolding);
      add(appleHolding);
    }};
    final Portfolio portfolio = new Portfolio(portfolioId, holdings);
    given(nationalStockService.getPrice(google.code)).willThrow(new Exception("Could Not Connect to Nation Stock Exchange!"));
    final double newApplePrice = 27.2;
    given(nationalStockService.getPrice(apple.code)).willReturn(newApplePrice);
    // When
    final Portfolio updatedPortfolio = portfolio.track(nationalStockService);
    // Then
    Double updatedNetWorth = (googleHolding.quantity * googleHolding.lastPrice) + (appleHolding.quantity * newApplePrice);
    assertThat(updatedPortfolio.networth(), is(updatedNetWorth));
  }
}

package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class HoldingSpecs {
  private final Stock apple = new Stock("Apple Inc.", "AAPL");
  private final Date now = new Date();
  private final Holding appleHolding = new Holding(now, apple, 10, 25.56);

  @Mock
  private NationalStockService nationalStockService;

  @Test
  public void netWorth() {
    assertThat(appleHolding.networth(), is(255.6));
  }

  @Test
  public void updatesNetWorthWhenUnderlyingStockHasNewTickerPrice() throws Exception {
    given(nationalStockService.getPrice(apple.code)).willReturn(27.2);
    // When
    final Holding updatedHolding = appleHolding.updateCurrentPrice(nationalStockService);
    // Then
    assertThat(updatedHolding.networth(), is(272.0));
  }

  @Test
  public void retainsOldNetWorthWhenNewPriceOfUnderlyingStockCouldNotBeObtained() throws Exception {
    // Given
    final double oldNetWorth = appleHolding.networth();
    given(nationalStockService.getPrice(apple.code)).willThrow(new Exception("Could Not Connect to Nation Stock Exchange!"));
    // When
    final Holding updatedHolding = appleHolding.updateCurrentPrice(nationalStockService);
    // Then
    assertThat(updatedHolding.networth(), is(oldNetWorth));
  }

  @Test
  public void equality() {
    Stock google = new Stock("Google Inc.", "GOOG");
    final Holding googleHolding = new Holding(now, google, 10, 25.56);
    assertThat(appleHolding, is(equalTo(appleHolding)));
    assertThat(appleHolding, is(not(equalTo(null))));
    assertThat(appleHolding, is(not(equalTo(googleHolding))));
  }
}

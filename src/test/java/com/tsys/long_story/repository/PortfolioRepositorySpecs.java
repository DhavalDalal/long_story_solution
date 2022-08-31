package com.tsys.long_story.repository;

import com.tsys.long_story.domain.Holding;
import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.domain.Stock;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationTest")
public class PortfolioRepositorySpecs {

  private final PortfolioRepository portfolioRepository = new PortfolioRepository(Map.of());

  @Test
  public void seedsRepositoryWithPortfoliosWhenStartedEmpty() {
    // When
    final List<Portfolio> portfolios = portfolioRepository.findAll();

    // Then
    assertFalse(portfolios.isEmpty());
  }

  @Test
  public void findsPortfolioById() {
    // Given
    final String portfolioId = "1";
    // When
    final Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
    // Then
    assertTrue(portfolio.isPresent());
    assertThat(portfolio.get().id, is(portfolioId));
  }

  @Test
  public void doesNotFindANonExistentPortfolio() {
    // Given
    final String portfolioId = "NON-EXISTENT";
    // When
    final Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
    // Then
    assertTrue(portfolio.isEmpty());
  }

  @Test
  public void savesAPortfolio() {
    // Given
    final String emptyId = "";
    final Portfolio portfolioWithoutId = new Portfolio(emptyId, List.of());
    // When
    final Portfolio saved = portfolioRepository.saveOrUpdate(portfolioWithoutId);
    // Then
    assertThat(saved.id, is(not(emptyId)));
  }

  @Test
  public void updatesAPortfolio() {
    // Given
    final Optional<Portfolio> portfolio = portfolioRepository.findById("1");
    Portfolio updated = addHolding(portfolio);
    // When
    final Portfolio savedUpdated = portfolioRepository.saveOrUpdate(updated);
    // Then
    assertThat(savedUpdated, is(portfolio.get()));
  }

  private Portfolio addHolding(Optional<Portfolio> portfolio) {
    final Stock google = new Stock("Google Inc.", "GOOG");
    final Date now = new Date();
    final Portfolio p = portfolio.orElseThrow();
    return p.add(now, google, 10, 45.66);
  }
}
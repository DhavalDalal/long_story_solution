package com.tsys.long_story.service.local;

import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.domain.Stock;
import com.tsys.long_story.repository.PortfolioRepository;
import com.tsys.long_story.service.remote.NationalStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@Tag("UnitTest")
public class PortfolioServiceSpecs {
    private final Stock apple = new Stock("Apple Inc.", "AAPL");
    private final Stock google = new Stock("Google Inc.", "GOOG");
    private final Date now = new Date();
    private final Portfolio portfolio1 = new Portfolio("1", new ArrayList<>()) {{
        add(now, apple, 8, 23.3);
        add(now, google, 78, 56.67);
    }};
    private final Portfolio portfolio2 = new Portfolio("2", new ArrayList<>()) {{
        add(now, google, 20, 23.3);
    }};

    private Map<String, Portfolio> portfolios = new HashMap<>() {{
        put(portfolio1.id, portfolio1);
        put(portfolio2.id, portfolio2);
    }};
    private PortfolioRepository portfolioRepository = new PortfolioRepository(portfolios);

    @Mock
    private NationalStockService nationalStockService;

    private PortfolioService portfolioService;

    @BeforeEach
    public void injectMocks() {
        portfolioService = new PortfolioService(nationalStockService, portfolioRepository);
    }

    @Test
    public void findsAllAvailablePortfolios() {
        // When
        final List<Portfolio> portfolios = portfolioService.findAll();
        // Then
        assertThat(portfolios, hasSize(2));
        assertThat(portfolios, hasItems(portfolio1, portfolio2));
    }

    @Test
    public void findsPortfolioByID() throws Exception {
        // Given
        // When
        final String portfolioId = "1";
        final Optional<Portfolio> portfolio = portfolioService.findById(portfolioId);
        // Then
        assertThat(portfolio.orElseThrow(() -> new Exception("Could Not find Portfolio for Id " + portfolioId)), is(portfolio1));
    }

    @Test
    public void doesNotFindPortfolioByaNonExistentID() {
        // Given
        // When
        final String portfolioId = "NON-EXISTENT";
        final Optional<Portfolio> portfolio = portfolioService.findById(portfolioId);
        // Then
        assertTrue(portfolio.isEmpty());
    }

    @Test
    public void netWorthOfIndividualPortfolios() {
        // When
        final Map<String, ?> netWorth = portfolioService.totalNetWorth();
        // Then
        final Map<String, Double> netWorthOfIndividualPortfolios = (Map<String, Double>) netWorth.get("portfolios");
        assertThat(netWorthOfIndividualPortfolios, is(Map.of("1", portfolio1.networth(), "2", portfolio2.networth())));
    }

    @Test
    public void totalNetWorthOfAllPortfolios() {
        // When
        final Map<String, ?> netWorth = portfolioService.totalNetWorth();
        // Then
        final Double totalNetWorth = (Double) netWorth.get("totalNetWorth");
        assertThat(totalNetWorth, is(portfolio1.networth() + portfolio2.networth()));
    }
}

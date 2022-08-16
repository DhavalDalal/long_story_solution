package com.tsys.long_story.service.local;

import com.tsys.long_story.domain.Holding;
import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.repository.PortfolioRepository;
import com.tsys.long_story.service.remote.NationalStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    private final NationalStockService stockService;
    private final PortfolioRepository repository;

    @Autowired
    public PortfolioService(NationalStockService stockService,
                            PortfolioRepository repository) {
        this.stockService = stockService;
        this.repository = repository;
    }

    public List<Portfolio> findAll() {
        return repository.findAll();
    }

    public Optional<Portfolio> findById(String portfolioId) {
        return repository.findById(portfolioId);
    }

    public Map<String, ?> totalNetWorth() {
        final Map<String, Double> networthOfPortfolios = findAll().stream()
            .collect(Collectors.toMap(portfolio -> portfolio.id, portfolio -> portfolio.networth()));
        final Double totalNetWorth = networthOfPortfolios.values()
            .stream()
            .reduce(0d, (a, b) -> a + b);
        return Map.of("totalNetWorth", totalNetWorth, "portfolios", networthOfPortfolios);
    }
}

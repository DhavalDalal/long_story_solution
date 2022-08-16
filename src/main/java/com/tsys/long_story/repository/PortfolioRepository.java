package com.tsys.long_story.repository;

import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.domain.Stock;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PortfolioRepository {
  private final Stock apple = new Stock("Apple Inc.", "AAPL");
  private final Stock google = new Stock("Google Inc.", "GOOG");
  private final Stock microsoft = new Stock("Microsoft Inc.", "MSFT");
  private final Stock oracle = new Stock("Oracle Inc.", "ORCL");
  private final Stock amazon = new Stock("Amazon Inc.", "AMZN");
  private final Date now = new Date();

  private Map<String, Portfolio> portfolios;

  private Map<String, Portfolio> seedPortfolios() {
    return new HashMap<>() {{
      put("1", new Portfolio("1", new ArrayList<>()) {{
        add(now, apple, 8, 23.3);
        add(now, google, 78, 56.67);
      }});
      put("2", new Portfolio("2", new ArrayList<>()) {{
        add(now, oracle, 45, 23.3);
        add(now, microsoft, 55, 56.67);
      }});
      put("3", new Portfolio("3", new ArrayList<>()) {{
        add(now, apple, 10, 13.3);
        add(now, google, 20, 23.3);
        add(now, microsoft, 30, 33.3);
        add(now, oracle, 40, 43.3);
        add(now, amazon, 50, 53.3);
      }});
    }};
  }

  public PortfolioRepository(Map<String, Portfolio> portfolios) {
    this.portfolios = (portfolios.isEmpty()) ? seedPortfolios() : portfolios;
  }

  public Optional<Portfolio> findById(String id) {
    return portfolios.containsKey(id) ?
            Optional.of(portfolios.get(id)) :
            Optional.empty();
  }

  public List<Portfolio> findAll() {
    return new ArrayList<>(portfolios.values());
  }

  public Portfolio saveOrUpdate(Portfolio portfolio) {
    var updated = portfolio.updateIdIfEmpty();
    portfolios.put(updated.id, updated);
    return updated;
  }
}

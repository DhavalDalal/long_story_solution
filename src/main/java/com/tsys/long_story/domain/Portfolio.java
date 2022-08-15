package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;

import java.util.*;
import java.util.stream.Stream;

public class Portfolio {
  public final String id;
  private final List<Holding> holdings;

  public Portfolio() {
    this(null, new ArrayList<>());
  }

  public Portfolio(final String id, final List<Holding> holdings) {
    this.id = id;
    this.holdings = holdings;
  }

  public Portfolio updateIdIfEmpty() {
    if (id == null || id.isEmpty() || id.isBlank())
      return new Portfolio(UUID.randomUUID().toString(), new ArrayList<>(holdings));

    return this;
  }

  // Cannot remove deprecated method as it is needed by Jackson
  // NOTE: Jackson requires public getter methods
  // or keep fields public and immutable
  @Deprecated()
  public List<Holding> getHoldings() {
    return Collections.unmodifiableList(holdings);
  }

  public Portfolio add(final Date date, final Stock stock, final int quantity, final double initialPrice) {
    return add(new Holding(date, stock, quantity, initialPrice));
  }

  public Portfolio add(final Holding holding) {
    holdings.add(holding);
    return this;
  }

  public Portfolio track(NationalStockService stockService) {
    Stream.iterate(0, index -> index + 1)
            .limit(holdings.size())
//            .parallel()
            .forEach(index ->
                    holdings.set(index, holdings.get(index).update(stockService)));
    return this;
  }

  public Double networth() {
    return holdings.stream()
            .mapToDouble(holding -> holding.networth())
            .sum();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + holdings.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;

    var other = (Portfolio) obj;
    return holdings.equals(other.holdings);
  }

  @Override
  public String toString() {
    return holdings.toString();
  }

}
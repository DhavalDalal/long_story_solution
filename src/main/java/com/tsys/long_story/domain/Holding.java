package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

public class Holding {
  public final Stock stock;
  public final int quantity;
  public final double purchasePrice;
  public final Date purchaseDate;
  public double currentPrice;
  public Date currentDate;
  private static final Logger LOG = Logger.getLogger(Holding.class.getName());

  public Holding(final Date purchaseDate, final Stock stock, final int quantity, final double purchasePrice) {
    this.purchaseDate = (purchaseDate == null) ? today() : purchaseDate;
    this.stock = stock;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
    this.currentPrice = purchasePrice;
  }

  public double networth() {
    return quantity * currentPrice;
  }

  public Holding updateCurrentPrice(NationalStockService stockService) {
    try {
      currentPrice = stock.price(stockService);
      currentDate = today();
    } catch (Exception e) {
      LOG.info(() -> String.format("Could Not update Price <== %s, Returning old value", e.getMessage()));
    }
    return this;
  }

  private Date today() {
    return Date.from(Instant.now());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Holding holding = (Holding) o;

    if (quantity != holding.quantity)
      return false;
    if (Double.compare(holding.purchasePrice, purchasePrice) != 0)
      return false;
    if (Double.compare(holding.currentPrice, currentPrice) != 0)
      return false;
    if (!stock.equals(holding.stock))
      return false;
    if (!purchaseDate.equals(holding.purchaseDate))
      return false;
    return currentDate.equals(holding.currentDate);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = stock.hashCode();
    result = 31 * result + quantity;
    temp = Double.doubleToLongBits(purchasePrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + purchaseDate.hashCode();
    temp = Double.doubleToLongBits(currentPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + currentDate.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s, %.2f, %.2f, %d, %.2f", stock.toString(), purchasePrice, currentPrice, quantity, networth());
  }
}

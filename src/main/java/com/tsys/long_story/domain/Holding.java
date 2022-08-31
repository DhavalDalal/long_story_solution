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
  public double lastPrice;
  public Date lastDate;
  private static final Logger LOG = Logger.getLogger(Holding.class.getName());

  public Holding(final Date purchaseDate, final Stock stock, final int quantity, final double purchasePrice) {
    this.purchaseDate = (purchaseDate == null) ? today() : purchaseDate;
    this.stock = stock;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
    this.lastPrice = purchasePrice;
  }

  public double networth() {
    return quantity * lastPrice;
  }

  public Holding updatePrice(NationalStockService stockService) {
    try {
      lastPrice = stock.price(stockService);
      lastDate = today();
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
    if (Double.compare(holding.lastPrice, lastPrice) != 0)
      return false;
    if (!stock.equals(holding.stock))
      return false;
    if (!purchaseDate.equals(holding.purchaseDate))
      return false;
    return lastDate.equals(holding.lastDate);
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
    temp = Double.doubleToLongBits(lastPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + lastDate.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s, %.2f, %.2f, %d, %.2f", stock.toString(), purchasePrice, lastPrice, quantity, networth());
  }
}

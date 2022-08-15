package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;

import java.util.Date;
import java.util.logging.Logger;

public class Holding {
  public final Date date;
  public final Stock stock;
  public final int quantity;
  public final double price;
  private static final Logger LOG = Logger.getLogger(Holding.class.getName());

  public Holding(final Date date, final Stock stock, final int quantity, final double price) {
    this.date = (date == null) ? new Date(): date;
    this.stock = stock;
    this.quantity = quantity;
    this.price = price;
  }

  public double networth() {
    return quantity * price;
  }

  public Holding update(NationalStockService stockService) {
    try {
      return new Holding(date, stock, quantity, stock.price(stockService));
    } catch (Exception e) {
      LOG.info(() -> String.format("No Response <== %s, Returning old value", e.getMessage()));
      return this;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(price);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + quantity;
    result = prime * result + ((stock == null) ? 0 : stock.hashCode());
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
    Holding other = (Holding) obj;
    if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
      return false;
    if (quantity != other.quantity)
      return false;
    if (stock == null) {
			return other.stock == null;
    } else return stock.equals(other.stock);
	}

  @Override
  public String toString() {
    return String.format("%s, %.2f, %d, %.2f", stock.toString(), price, quantity, networth());
  }
}

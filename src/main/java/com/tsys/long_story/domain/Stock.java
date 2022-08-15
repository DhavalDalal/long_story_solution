package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;

public class Stock {
  public final String name;
  public final String code;

  public Stock(final String name, final String code) {
    this.name = name;
    this.code = code;
  }

  public double price(NationalStockService stockService) throws Exception {
    try {
      return stockService.getPrice(code);
    } catch (Exception e) {
      throw new Exception(String.format("Could Not Get Price for ticker %s, %s", code, e.getMessage()));
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
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
    Stock other = (Stock) obj;
    if (code == null) {
			return other.code == null;
    } else return code.equals(other.code);
	}

  @Override
  public String toString() {
    return String.format("%s, %s", name, code);
  }
}

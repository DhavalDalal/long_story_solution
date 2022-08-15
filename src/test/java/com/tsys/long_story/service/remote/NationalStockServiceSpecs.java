package com.tsys.long_story.service.remote;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("IntegrationTest")
public class NationalStockServiceSpecs {

    @Test
    public void getPriceForAValidStockCode() throws Exception {
        final NationalStockService nationalStockService = new NationalStockService("https://national-stock-service.herokuapp.com", true);
        assertThat(nationalStockService.getPrice("MSFT"), is(notNullValue()));
    }

    @Test
    public void shoutsWhenGettingPriceForAnInvalidStock() throws Exception {
        final NationalStockService nationalStockService = new NationalStockService("https://national-stock-service.herokuapp.com", true);
        assertThrows(FileNotFoundException.class, () -> nationalStockService.getPrice("OOPS"));
    }

    @Test
    public void shoutsWhenGetPriceIsNotAllowedToMakeCalls() throws Exception {
        final NationalStockService nationalStockService = new NationalStockService("https://national-stock-service.herokuapp.com", false);
        assertThrows(UnsupportedOperationException.class, () -> nationalStockService.getPrice("MSFT"), "Operation Not Allowed At This Time!");
    }

}

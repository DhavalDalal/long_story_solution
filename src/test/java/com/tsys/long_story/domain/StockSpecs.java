package com.tsys.long_story.domain;

import com.tsys.long_story.service.remote.NationalStockService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class StockSpecs {
    private final Stock apple = new Stock("Apple Inc.", "AAPL");

    @Mock
    private NationalStockService nationalStockService;

    @Test
    public void fetchesCurrentPrice() throws Exception {
        final double applePrice = 25.46;
        given(nationalStockService.getPrice(apple.code)).willReturn(applePrice);
        assertThat(apple.price(nationalStockService), is(applePrice));
    }

    @Test
    public void shoutsWhenItCannotFetchCurrentPrice() throws Exception {
        given(nationalStockService.getPrice(apple.code)).willThrow(new Exception("Cannot Connect to National Stock Exchange"));
        assertThrows(Exception.class, () -> apple.price(nationalStockService));
    }

    @Test
    public void equality() {
        Stock google = new Stock("Google Inc.", "GOOG");
        assertThat(apple, is(equalTo(apple)));
        assertThat(apple, is(not(equalTo(null))));
        assertThat(apple, is(not(equalTo(google))));
    }
}

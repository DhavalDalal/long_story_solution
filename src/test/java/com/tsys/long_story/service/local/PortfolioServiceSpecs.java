package com.tsys.long_story.service.local;

import com.tsys.long_story.repository.PortfolioRepository;
import com.tsys.long_story.service.remote.NationalStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("UnitTest")
public class PortfolioServiceSpecs {
    @Mock
    private NationalStockService nationalStockService;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private PortfolioService portfolioService;

//    @BeforeEach
//    public void setup() {
//        portfolioService = new PortfolioService(nationalStockService, portfolioRepository);
//    }

    @Test
    public void returnsAllAvailablePortfolios() {
        // Given
//        FraudStatus pass = new FraudStatus("pass");
//        given(fraudCheckerClient.checkFraud(validCard, amount)).willReturn(pass);
//        given(uuidGenerator.generate()).willReturn(uuid);

        // When
//        final Optional<TransactionReference> transactionReference = paymentsService.makePayment(new Order("TEST-ORDER-ID", List.of(
//                new Item(1L, "Dant Kanti Toothpaste", new Money(Currency.getInstance("INR"), 123.545), 10))
//        ), validCard);

        // Then
//        assertThat(transactionReference, is(Optional.of(new TransactionReference(uuid, now, "accepted"))));
    }

    @Test
    public void returnsNetWorthOfAllPortfolios() {
    }

    @Test
    public void returnsTotalNetWorthOfAllPortfolios() {
    }
}

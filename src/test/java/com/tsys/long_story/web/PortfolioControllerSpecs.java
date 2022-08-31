package com.tsys.long_story.web;

import com.tsys.long_story.domain.Holding;
import com.tsys.long_story.domain.Portfolio;
import com.tsys.long_story.domain.Stock;
import com.tsys.long_story.service.local.PortfolioService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// For Junit4, use @RunWith
// @RunWith(SpringRunner.class)
// For Junit5, use @ExtendWith
// SpringExtension.class provides a bridge between Spring Boot test features
// and JUnit. Whenever we use any Spring Boot testing features in our JUnit
// tests, this annotation will be required.
@ExtendWith(SpringExtension.class)
// We're only testing the web layer, we use the @WebMvcTest
// annotation. It allows us to easily test requests and responses
// using the set of static methods implemented by the
// MockMvcRequestBuilders and MockMvcResultMatchers classes.
//
// Using the @WebMvcTest Annotation we are loading Spring's
// WebApplication Context and hence all Controller Advices and Filters
// get automatically applied.
//
// We verify the validation behavior by applying Validation Advice, it
// is automatically available, because we are using @WebMvcTest annotation.
//
// NOTE: No Web-Server is deployed
@WebMvcTest(PortfolioController.class)
@Tag("UnitTest")
public class PortfolioControllerSpecs {
    @MockBean
    private PortfolioService portfolioService;
    private final Stock apple = new Stock("Apple Inc.", "AAPL");

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homesToIndexPage() throws Exception {
        final var request = givenRequestFor("/", false);
        final ResultActions resultActions = whenTheRequestIsMade(request);
        thenExpect(resultActions,
                MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getsAllPortfolios() throws Exception {
        final var request = givenRequestFor("/portfolio", false);
        final ResultActions resultActions = whenTheRequestIsMade(request);
        thenExpect(resultActions,
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void findsPortfolioById() throws Exception {
        final String portfolioId = "1";
        given(portfolioService.findById(portfolioId)).willReturn(Optional.of(new Portfolio(portfolioId, List.of(new Holding(new Date(), apple, 10, 25.56)))));
        final var request = givenRequestFor("/portfolio/" + portfolioId, false);
        final ResultActions resultActions = whenTheRequestIsMade(request);
        thenExpect(resultActions,
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$").isMap());
    }

    @Test
    public void getsNetWorthOfAllPortfolios() throws Exception {
        final var request = givenRequestFor("/portfolio/networth", false);
        final ResultActions resultActions = whenTheRequestIsMade(request);
        thenExpect(resultActions,
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$").isMap());
    }

    @Test
    public void trackReturnsNoContent() throws Exception {
        when(this.portfolioService.track("1")).thenReturn(Optional.of("1"));
        final var request = givenRequestFor("/portfolio/1/track", false);
        final ResultActions resultActions = whenTheRequestIsMade(request);
        thenExpect(resultActions,
                MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void trackTracksAGivenPortfolio() throws Exception {
        final var request = givenRequestFor("/portfolio/1/track", false);
        whenTheRequestIsMade(request);
        verify(this.portfolioService).track("1");
    }

    @Test
    public void trackReturnsNotFoundGivenNonExistentPortfolio() throws Exception {
        final var nonExistentId = "NON-EXISTENT-ID";
        when(this.portfolioService.track(nonExistentId)).thenReturn(Optional.empty());
        final var request = givenRequestFor(String.format("/portfolio/%s/track", nonExistentId), false);
        final ResultActions resultActions = whenTheRequestIsMade(request);
        thenExpect(resultActions,
                MockMvcResultMatchers.status().isNotFound());
    }

    private MockHttpServletRequestBuilder givenRequestFor(String url, boolean isPostRequest) {
        if (isPostRequest)
            return MockMvcRequestBuilders.post(url).characterEncoding("UTF-8");

        return MockMvcRequestBuilders.get(url).characterEncoding("UTF-8");
    }

    private ResultActions whenTheRequestIsMade(MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    private void thenExpect(ResultActions resultActions, ResultMatcher... matchers) throws Exception {
        resultActions.andExpect(ResultMatcher.matchAll(matchers));
    }
}


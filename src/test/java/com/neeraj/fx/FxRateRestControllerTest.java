package com.neeraj.fx;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.neeraj.fx.Transformer.DATE_TIME_FORMATTER;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = FxRateRestController.class)
public class FxRateRestControllerTest {

    @MockBean
    FxRateCache fxRateCache;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testFxRate() {
        FxRate fxRate = new FxRate(106L, "EUR/USD", new BigDecimal("1.1000"), new BigDecimal("1.2000"), LocalDateTime.parse("01-06-2020 12:01:01:001", DATE_TIME_FORMATTER));

        when(fxRateCache.get("EUR/USD")).thenReturn(Optional.of(fxRate));

        webClient.get().uri("/fxRate/{baseCcy}/{quoteCcy}", "EUR", "USD")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody(FxRate.class);

        verify(fxRateCache, times(1)).get("EUR/USD");
    }


    @Test
    void testFxRates() {
        List<FxRate> fxRates = Stream.of(new FxRate(106L, "EUR/USD", new BigDecimal("1.1000"), new BigDecimal("1.2000"), LocalDateTime.parse("01-06-2020 12:01:01:001", DATE_TIME_FORMATTER)),
                        new FxRate(107L, "EUR/JPY", new BigDecimal("119.60"), new BigDecimal("119.90"), LocalDateTime.parse("01-06-2020 12:01:02:002", DATE_TIME_FORMATTER)),
                        new FxRate(108L, "GBP/USD", new BigDecimal("1.2500"), new BigDecimal("1.2560"), LocalDateTime.parse("01-06-2020 12:01:02:002", DATE_TIME_FORMATTER)),
                        new FxRate(109L, "GBP/USD", new BigDecimal("1.2499"), new BigDecimal("1.2561"), LocalDateTime.parse("01-06-2020 12:01:02:100", DATE_TIME_FORMATTER)),
                        new FxRate(110L, "EUR/JPY", new BigDecimal("119.61"), new BigDecimal("119.91"), LocalDateTime.parse("01-06-2020 12:01:02:110", DATE_TIME_FORMATTER)))
                .collect(Collectors.toList());

        when(fxRateCache.values()).thenReturn(fxRates);

        webClient.get().uri("/fxRates")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FxRate.class);

        verify(fxRateCache, times(1)).values();
    }
}

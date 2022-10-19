package com.neeraj.fx;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.neeraj.fx.Transformer.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FxRateCacheTest {

    @Test
    public void testPutDuplicateKey() {
        FxRateCache fxRateCache = new FxRateCache();
        FxRate eurJpy1 = new FxRate(107L, "EUR/JPY", new BigDecimal("119.60"), new BigDecimal("119.90"), LocalDateTime.parse("01-06-2020 12:01:02:002", DATE_TIME_FORMATTER));
        fxRateCache.put(eurJpy1.ccyPair(), eurJpy1);
        assertEquals(eurJpy1, fxRateCache.get(eurJpy1.ccyPair()).get());

        FxRate gbpUsd2 = new FxRate(109L, "GBP/USD", new BigDecimal("1.2499"), new BigDecimal("1.2561"), LocalDateTime.parse("01-06-2020 12:01:02:100", DATE_TIME_FORMATTER));
        fxRateCache.put(gbpUsd2.ccyPair(), gbpUsd2);
        assertEquals(gbpUsd2, fxRateCache.get(gbpUsd2.ccyPair()).get());

        FxRate gbpUsd1 = new FxRate(108L, "GBP/USD", new BigDecimal("1.2500"), new BigDecimal("1.2560"), LocalDateTime.parse("01-06-2020 12:01:02:002", DATE_TIME_FORMATTER));
        fxRateCache.put(gbpUsd1.ccyPair(), gbpUsd1);
        assertEquals(gbpUsd2, fxRateCache.get(gbpUsd2.ccyPair()).get());

        FxRate eurJpy2 = new FxRate(110L, "EUR/JPY", new BigDecimal("119.61"), new BigDecimal("119.91"), LocalDateTime.parse("01-06-2020 12:01:02:110", DATE_TIME_FORMATTER));
        fxRateCache.put(eurJpy2.ccyPair(), eurJpy2);
        assertEquals(eurJpy2, fxRateCache.get(eurJpy2.ccyPair()).get());
    }

}

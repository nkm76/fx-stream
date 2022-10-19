package com.neeraj.fx;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

import static com.neeraj.fx.Transformer.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformerTest {

    @Test
    public void testToFxRates() {
        Collection<FxRate> fxRates = Transformer.toFxRates("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001\n" +
                "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        Iterator<FxRate> iterator = fxRates.iterator();
        var eurUsd = iterator.next();
        assertEquals(106L, eurUsd.id());
        assertEquals("EUR/USD", eurUsd.ccyPair());
        assertEquals(new BigDecimal("1.1000"), eurUsd.bidPrice());
        assertEquals(new BigDecimal("1.2000"), eurUsd.askPrice());
        assertEquals(LocalDateTime.parse("01-06-2020 12:01:01:001", DATE_TIME_FORMATTER), eurUsd.timestamp());

        var eurJpy = iterator.next();
        assertEquals(107L, eurJpy.id());
        assertEquals("EUR/JPY", eurJpy.ccyPair());
        assertEquals(new BigDecimal("119.60"), eurJpy.bidPrice());
        assertEquals(new BigDecimal("119.90"), eurJpy.askPrice());
        assertEquals(LocalDateTime.parse("01-06-2020 12:01:02:002", DATE_TIME_FORMATTER), eurJpy.timestamp());
    }
}

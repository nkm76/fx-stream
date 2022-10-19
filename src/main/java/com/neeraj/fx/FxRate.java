package com.neeraj.fx;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Record of price details
 *
 * @param id        Unique Id.
 * @param ccyPair   base/quote currency
 * @param bidPrice  Bid price
 * @param askPrice  Ask price
 * @param timestamp timestamp
 */
public record FxRate(Long id, String ccyPair, BigDecimal bidPrice, BigDecimal askPrice, LocalDateTime timestamp) {
}

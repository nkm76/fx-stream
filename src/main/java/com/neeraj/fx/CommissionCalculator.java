package com.neeraj.fx;

import java.math.BigDecimal;

/**
 * Defines functions which apply commissions
 */
public interface CommissionCalculator {

    // Commissions are stored are %ages
    BigDecimal DEFAULT_BID_COMMISSION = new BigDecimal("0.1");
    BigDecimal DEFAULT_ASK_COMMISSION = new BigDecimal("0.1");

    static BigDecimal applyBidCommission(BigDecimal value) {
        return value.multiply(BigDecimal.ONE.subtract(DEFAULT_BID_COMMISSION.movePointLeft(2)));
    }

    static BigDecimal applyAskCommission(BigDecimal value) {
        return value.multiply(BigDecimal.ONE.add(DEFAULT_ASK_COMMISSION.movePointLeft(2)));
    }

    static FxRate applyCommissions(FxRate fxRate) {
        return new FxRate(fxRate.id(), fxRate.ccyPair(), applyBidCommission(fxRate.bidPrice()), applyAskCommission(fxRate.askPrice()), fxRate.timestamp());
    }
}

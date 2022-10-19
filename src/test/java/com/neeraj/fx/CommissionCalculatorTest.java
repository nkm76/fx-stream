package com.neeraj.fx;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommissionCalculatorTest {

    @Test
    public void testApplyBidCommission() {
        assertEquals(new BigDecimal("99.900"), CommissionCalculator.applyBidCommission(new BigDecimal(100)));
    }

    @Test
    public void testApplyAskCommission() {
        assertEquals(new BigDecimal("100.100"), CommissionCalculator.applyAskCommission(new BigDecimal(100)));
    }
}

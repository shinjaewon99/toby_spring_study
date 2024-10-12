package com.example.toby_spring.payment;

import com.example.toby_spring.exrate.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class ExRateProviderStub implements ExRateProvider {
    private BigDecimal exRate;

    public BigDecimal getExRate() {
        return exRate;
    }

    public void setExRate(final BigDecimal exRate) {
        this.exRate = exRate;
    }

    public ExRateProviderStub(final BigDecimal exRate) {
        this.exRate = exRate;
    }

    @Override
    public BigDecimal getExRate(final String currency) throws IOException {
        return exRate;
    }
}

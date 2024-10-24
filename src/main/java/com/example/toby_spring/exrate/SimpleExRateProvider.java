package com.example.toby_spring.exrate;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SimpleExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(final String currency) {
        if (currency.equals("USD")) return BigDecimal.valueOf(1000);

        throw new IllegalArgumentException("지원되지 않는 통화 입니다.");
    }
}

package com.example.toby_spring;

import java.io.IOException;
import java.math.BigDecimal;

public class SimpleExRateProvider {
    BigDecimal getExRate(final String currency) throws IOException {
        if(currency.equals("USD")) return BigDecimal.valueOf(1000);

        throw new IllegalArgumentException("지원되지 않는 통화 입니다.");
    }
}

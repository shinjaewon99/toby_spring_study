package com.example.toby_spring.exrate;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExRateProvider {
    BigDecimal getExRate(final String currency) throws IOException;
}

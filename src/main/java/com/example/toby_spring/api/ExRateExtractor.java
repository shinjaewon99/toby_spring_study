package com.example.toby_spring.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface ExRateExtractor {
    BigDecimal extract(final String response) throws JsonProcessingException;
}

package com.example.toby_spring.api;

import com.example.toby_spring.exrate.ExRateDate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class ErApiExRateExtractor implements ExRateExtractor{
    @Override
    public BigDecimal extract(final String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateDate data = mapper.readValue(response, ExRateDate.class);
        return data.rates().get("KRW");
    }
}

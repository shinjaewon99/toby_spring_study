package com.example.toby_spring.exrate;

import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class RestTemplateExRateProvider implements ExRateProvider {
    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(final String currency) {
        final String url = "https://open.er-api.com/v6/latest/" + currency;

        // HTTP 응답의 Body 부분을 변환
        return restTemplate.getForObject(url, ExRateDate.class).rates().get("KRW");
    }
}

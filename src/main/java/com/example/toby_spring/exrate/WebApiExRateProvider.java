package com.example.toby_spring.exrate;

import com.example.toby_spring.api.ApiTemplate;
import com.example.toby_spring.api.ErApiExRateExtractor;
import com.example.toby_spring.api.HttpClientExecutor;

import java.math.BigDecimal;

public class WebApiExRateProvider implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(final String currency) {
        final String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url, new HttpClientExecutor(), new ErApiExRateExtractor());

        // 아래 처럼 람다로 구현 해도 됨
        /**
         * return runApiForExRate(url, new SimpleApiExecutor(), response -> {
         *             ObjectMapper mapper = new ObjectMapper();
         *             ExRateDate data = mapper.readValue(response, ExRateDate.class);
         *             return data.rates().get("KRW");
         *         });
         */
    }
}

package com.example.toby_spring.exrate;

import com.example.toby_spring.api.ApiExecutor;
import com.example.toby_spring.api.ErApiExRateExtractor;
import com.example.toby_spring.api.ExRateExtractor;
import com.example.toby_spring.api.SimpleApiExecutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(final String currency) {
        final String url = "https://open.er-api.com/v6/latest/" + currency;


        return runApiForExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());

        // 아래 처럼 람다로 구현 해도 됨
        /**
         * return runApiForExRate(url, new SimpleApiExecutor(), response -> {
         *             ObjectMapper mapper = new ObjectMapper();
         *             ExRateDate data = mapper.readValue(response, ExRateDate.class);
         *             return data.rates().get("KRW");
         *         });
         */
    }

    private static BigDecimal runApiForExRate(final String url, final ApiExecutor apiExecutor, final ExRateExtractor exRateExtractor) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        /**
         * HttpURLConnection이라는 서브 클래스로 캐스팅해야, 추가적인 http 기능을 사용 할 수 있다.
         */

        final String response;
        try {
            response = apiExecutor.execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exRateExtractor.extract(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

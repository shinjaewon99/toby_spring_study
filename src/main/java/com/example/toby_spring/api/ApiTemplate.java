package com.example.toby_spring.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {
    private final ApiExecutor apiExecutor;
    private final ExRateExtractor exRateExtractor;

    public ApiTemplate() {
        this.apiExecutor = new HttpClientExecutor();
        this.exRateExtractor = new ErApiExRateExtractor();
    }

    public ApiTemplate(final ApiExecutor apiExecutor, final ExRateExtractor exRateExtractor) {
        this.apiExecutor = apiExecutor;
        this.exRateExtractor = exRateExtractor;
    }

    // default 콜백
    public BigDecimal getForExRate(final String url) {
        return this.getForExRate(url, this.apiExecutor, this.exRateExtractor);
    }

    public BigDecimal getForExRate(final String url, final ApiExecutor apiExecutor) {
        return this.getForExRate(url, apiExecutor, this.exRateExtractor);
    }

    public BigDecimal getForExRate(final String url, final ExRateExtractor exRateExtractor) {
        return this.getForExRate(url, this.apiExecutor, exRateExtractor);
    }

    public BigDecimal getForExRate(final String url, final ApiExecutor apiExecutor, final ExRateExtractor exRateExtractor) {
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

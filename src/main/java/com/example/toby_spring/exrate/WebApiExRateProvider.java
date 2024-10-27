package com.example.toby_spring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(final String currency) {
        final String url = "https://open.er-api.com/v6/latest/" + currency;
        return runApiForExRate(url);
    }

    private static BigDecimal runApiForExRate(final String url) {
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
            response = executeApi(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static BigDecimal extractExRate(final String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateDate data = mapper.readValue(response, ExRateDate.class);
        return data.rates().get("KRW");
    }

    private static String executeApi(final URI uri) throws IOException {
        final String response;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        // auto close
        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            response = br.lines().collect(Collectors.joining());
        }
        return response;
    }
}

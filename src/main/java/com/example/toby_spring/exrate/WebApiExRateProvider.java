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
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

            // auto close
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = br.lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            ExRateDate data = mapper.readValue(response, ExRateDate.class);
            return data.rates().get("KRW");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

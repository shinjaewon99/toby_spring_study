package com.example.toby_spring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount) throws IOException {
        // todo : 환율 가져오기
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);

        /**
         * HttpURLConnection이라는 서브 클래스로 캐스팅해야, 추가적인 http 기능을 사용 할 수 있다.
         */
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateDate data = mapper.readValue(response, ExRateDate.class);
        BigDecimal exRate = data.rates().get("KRW");

        // todo : 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // todo : 유효시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount,
                validUntil);
    }

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment);
    }
}

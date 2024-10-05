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

abstract class PaymentService {

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = getExRate(currency);

        // todo : 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // todo : 유효시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount,
                validUntil);
    }

    abstract BigDecimal getExRate(final String currency) throws IOException;

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new WebApiExRatePaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment);
    }
}

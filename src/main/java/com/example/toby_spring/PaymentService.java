package com.example.toby_spring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    private final ExRateProvider exRateProvider;

    // prepare 메소드를 여러본 호출해도 매번 WebApiExRateProvider 클래스를 계속 호출 하지 않아도 됨
    public PaymentService() {
        this.exRateProvider = new WebApiExRateProvider();
    }

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getExRate(currency);

        // todo : 금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        // todo : 유효시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount,
                validUntil);
    }
}

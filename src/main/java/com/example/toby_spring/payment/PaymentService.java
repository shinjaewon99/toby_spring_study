package com.example.toby_spring.payment;

import com.example.toby_spring.exrate.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class PaymentService {
    private final ExRateProvider exRateProvider;
    private final Clock clock;

    // prepare 메소드를 여러본 호출해도 매번 WebApiExRateProvider 클래스를 계속 호출 하지 않아도 됨
    public PaymentService(ExRateProvider exRateProvider, Clock clock) {

        this.exRateProvider = exRateProvider;
        this.clock = clock;
        /**
         *  역할에 대한 책임을 PaymentService가 갖지 않음
         this.exRateProvider = new WebApiExRateProvider();
         **/
    }

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount) {
        BigDecimal exRate = exRateProvider.getExRate(currency);

        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRate, LocalDateTime.now(clock));
    }
}

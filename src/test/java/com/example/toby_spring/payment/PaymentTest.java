package com.example.toby_spring.payment;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest {

    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", TEN, valueOf(1_000), LocalDateTime.now(clock)
        );

        assertThat(payment.getConvertedAmount()).isEqualTo(valueOf(10_000));
        assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValid() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", TEN, valueOf(1_000), LocalDateTime.now(clock)
        );

        assertThat(payment.isValid(clock)).isTrue();
        // 30분 이전으로 시간 변경.
        assertThat(payment.isValid(Clock.offset(clock,
                Duration.of(30, ChronoUnit.MINUTES)))).isTrue();
    }
}

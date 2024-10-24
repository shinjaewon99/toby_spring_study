package com.example.toby_spring.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {
    Clock clock;

    @BeforeEach
    void beforeEach() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    void prepare() {

        testAmount(valueOf(500), valueOf(5_000), this.clock);
        testAmount(valueOf(1000), valueOf(10_000), this.clock);
        // 원화 환산금액의 유효 시간
        // assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
    }

    @Test
    void validUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // valid until이 prepare() 30분 뒤로 설정 됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    private static void testAmount(final BigDecimal exRate, final BigDecimal convertedAmount, final Clock clock) {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // 환율 정보를 가져온다.
        // 금액을 비교하기 위해서는 isEqualsTo 보단, isEqualByComparingTo 사용 권장
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(500L));

        // 원화환산금액 계산
        assertThat(payment.getConvertedAmount())
                .isEqualTo(convertedAmount);
    }
}

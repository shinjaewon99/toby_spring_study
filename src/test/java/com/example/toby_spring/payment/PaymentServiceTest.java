package com.example.toby_spring.payment;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    @Test
    void prepare() throws IOException {
        testAmount(valueOf(500), valueOf(5_000));
        testAmount(valueOf(1000), valueOf(10_000));
        // 원화 환산금액의 유효 시간
        // assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
    }

    private static void testAmount(final BigDecimal exRate, final BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

        Payment payment = paymentService.prepare(1L, "USD", TEN);

        // 환율 정보를 가져온다.
        // 금액을 비교하기 위해서는 isEqualsTo 보단, isEqualByComparingTo 사용 권장
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(500L));

        // 원화환산금액 계산
        assertThat(payment.getConvertedAmount())
                .isEqualTo(convertedAmount);
    }
}

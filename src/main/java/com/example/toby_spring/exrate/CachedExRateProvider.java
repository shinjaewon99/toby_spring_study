package com.example.toby_spring.exrate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider target;
    private BigDecimal cachedExRate;
    private LocalDateTime cacheExpiryTime;

    public CachedExRateProvider(final ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(final String currency) throws IOException {
        if (cachedExRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())){
            cachedExRate = this.target.getExRate(currency);

            // 캐싱한 환율에 대해 시간을 기준으로 업데이트를 함
            cacheExpiryTime = LocalDateTime.now().plusSeconds(3);
            System.out.println("Cache Update");
        }

        return cachedExRate;
    }
}

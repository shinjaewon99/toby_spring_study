package com.example.toby_spring;

import com.example.toby_spring.exrate.CachedExRateProvider;
import com.example.toby_spring.exrate.ExRateProvider;
import com.example.toby_spring.exrate.WebApiExRateProvider;
import com.example.toby_spring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ComponentScan
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

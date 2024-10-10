package com.example.toby_spring;

import com.example.toby_spring.exrate.CachedExRateProvider;
import com.example.toby_spring.exrate.ExRateProvider;
import com.example.toby_spring.exrate.WebApiExRateProvider;
import com.example.toby_spring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }
}

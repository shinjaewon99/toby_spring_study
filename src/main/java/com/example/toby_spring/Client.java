package com.example.toby_spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) throws IOException {
        // 구현한 ObjectFactory 클래스의 구성정보를 참고하여 사용
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);

        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println(payment);
    }
}

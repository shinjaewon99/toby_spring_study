package com.example.toby_spring;

import com.example.toby_spring.payment.Payment;
import com.example.toby_spring.payment.PaymentService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 구현한 ObjectFactory 클래스의 구성정보를 참고하여 사용
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);

        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));

        System.out.println("payment1" + payment1);
        System.out.println("==============캐시 사용=================");

        TimeUnit.SECONDS.sleep(1);
        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment2" + payment2);

        TimeUnit.SECONDS.sleep(2);
        Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment3" + payment3);
    }
}

package com.example.toby_spring;

import com.example.toby_spring.data.OrderRepository;
import com.example.toby_spring.order.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository repository = beanFactory.getBean(OrderRepository.class);

        Order order1 = new Order("100", BigDecimal.TEN);
        repository.save(order1);

        Order order2 = new Order("100", BigDecimal.ONE);
        repository.save(order2);


    }
}

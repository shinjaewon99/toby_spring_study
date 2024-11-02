package com.example.toby_spring;

import com.example.toby_spring.data.JpaOrderRepository;
import com.example.toby_spring.order.OrderRepository;
import com.example.toby_spring.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {
    @Bean
    public OrderService orderService(JpaTransactionManager transactionManager) {
        return new OrderService(orderRepository(), transactionManager);
    }

    @Bean
    public OrderRepository orderRepository() {
        return new JpaOrderRepository();
    }
}
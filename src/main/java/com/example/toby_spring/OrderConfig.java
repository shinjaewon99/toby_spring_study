package com.example.toby_spring;

import com.example.toby_spring.data.JdbcOrderRepository;
import com.example.toby_spring.order.OrderRepository;
import com.example.toby_spring.order.OrderService;
import com.example.toby_spring.order.OrderServiceImpl;
import com.example.toby_spring.order.OrderServiceTxProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {
    @Bean
    public OrderService orderService(
            OrderRepository orderRepository,
            JpaTransactionManager transactionManager) {
        return new OrderServiceTxProxy(new OrderServiceImpl(orderRepository), transactionManager);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }
}

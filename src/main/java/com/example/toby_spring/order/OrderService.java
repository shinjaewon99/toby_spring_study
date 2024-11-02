package com.example.toby_spring.order;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final JpaTransactionManager transactionManager;

    public OrderService(final OrderRepository orderRepository, final JpaTransactionManager transactionManager) {

        this.orderRepository = orderRepository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(final String no, final BigDecimal total) {
        Order order = new Order(no, total);

        return new TransactionTemplate(transactionManager).execute(status -> {
            this.orderRepository.save(order);
            return order;

        });
    }
}

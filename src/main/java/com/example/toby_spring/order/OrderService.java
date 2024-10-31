package com.example.toby_spring.order;

import com.example.toby_spring.data.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(final String no, final BigDecimal total) {
        Order order = new Order(no, total);

        this.orderRepository.save(order);

        return order;
    }
}

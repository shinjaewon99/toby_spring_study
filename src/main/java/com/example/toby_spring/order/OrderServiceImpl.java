package com.example.toby_spring.order;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(final String no, final BigDecimal total) {
        Order order = new Order(no, total);
        this.orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> createOrders(List<OrderReq> reqs) {
        // 하나의 트랜잭션으로 묶임
        return reqs.stream().map(req ->
                createOrder(req.no(), req.total())).toList();
    }
}

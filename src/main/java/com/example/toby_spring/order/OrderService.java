package com.example.toby_spring.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    // JDBC를 사용하든, JPA를 사용하든 PlatformTr- 가 알아서 처리
    private final PlatformTransactionManager transactionManager;

    public OrderService(final OrderRepository orderRepository, final PlatformTransactionManager transactionManager) {

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

    public List<Order> createOrders(List<OrderReq> reqs) {
        // 하나의 트랜잭션으로 묶임
        return new TransactionTemplate(transactionManager)
                .execute(status -> reqs.stream().map(req ->
                createOrder(req.no(), req.total())).toList());
    }
}

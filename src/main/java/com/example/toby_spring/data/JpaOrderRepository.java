package com.example.toby_spring.data;

import com.example.toby_spring.order.Order;
import com.example.toby_spring.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JpaOrderRepository implements OrderRepository {
    // 영속성 관리를 위해 사용
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

}

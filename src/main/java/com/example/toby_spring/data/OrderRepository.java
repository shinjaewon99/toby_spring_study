package com.example.toby_spring.data;

import com.example.toby_spring.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class OrderRepository {
    private final EntityManagerFactory emf;

    public OrderRepository(final EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {
        // em
        EntityManager em = emf.createEntityManager();

        // transaction
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            // em.persist
            em.persist(order);
            em.flush();

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            if (em.isOpen()) em.close();
        }
        em.close();
    }
}

package com.example.toby_spring;

import com.example.toby_spring.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory.class);

        // em
        EntityManager em = emf.createEntityManager();

        // transaction
        em.getTransaction().begin();

        Order order = new Order("100", BigDecimal.TEN);
        // em.persist

        em.persist(order);

        em.getTransaction().commit();
        em.close();
    }
}

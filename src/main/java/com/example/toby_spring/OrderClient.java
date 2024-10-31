package com.example.toby_spring;

import com.example.toby_spring.data.OrderRepository;
import com.example.toby_spring.order.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

public class OrderClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository repository = beanFactory.getBean(OrderRepository.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);
        try {
            new TransactionTemplate(transactionManager).execute(status -> {
                Order order1 = new Order("100", BigDecimal.TEN);
                repository.save(order1);

                Order order2 = new Order("100", BigDecimal.ONE);
                repository.save(order2);
                return null;
            });
        } catch (DataIntegrityViolationException e) {
            System.out.println("주문번호 중복 작업");
        }

        // transaction begin

        // commit
    }
}

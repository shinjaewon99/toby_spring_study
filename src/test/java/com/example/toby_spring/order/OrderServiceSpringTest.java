package com.example.toby_spring.order;

import com.example.toby_spring.OrderConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {

    @Autowired
    OrderService orderService;

    // bean으로 등록한 DataSource를 받아온다. (DataConfig.class)
    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
        // 명확하게 표현되는 return 값은 아래와 같이 var 타입을 사용하는 것도 좋음
        var order = orderService.createOrder("0100", BigDecimal.ONE);

        assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
    void createOrders() {
        List<OrderReq> orderReqs = List.of(
                new OrderReq("0200", BigDecimal.ONE),
                new OrderReq("0201", BigDecimal.TEN)
        );

        var orders = orderService.createOrders(orderReqs);
        assertThat(orders).hasSize(2);
        orders.forEach(order -> {
            assertThat(order.getId()).isGreaterThan(0);
        });
    }

    @Test
    void createDuplicateOrders() {
        List<OrderReq> orderReqs = List.of(
                new OrderReq("0300", BigDecimal.ONE),
                new OrderReq("0300", BigDecimal.TEN)
        );

        assertThatThrownBy(() -> orderService.createOrders(orderReqs))
                .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);

        var count = client.sql("select count(*) from orders where no = '0300").query(Long.class).single();
        // createOrder 메소드를 실행할때 각각의 트랜잭션으로 묶여서 실행되므로 0300 하나가 저장되며 count 는 1이 반환
        assertThat(count).isEqualTo(0);


    }
}

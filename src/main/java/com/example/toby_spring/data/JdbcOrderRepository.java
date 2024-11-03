package com.example.toby_spring.data;

import com.example.toby_spring.order.Order;
import com.example.toby_spring.order.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;

public class JdbcOrderRepository implements OrderRepository {
    private final JdbcClient jdbcClient;

    public JdbcOrderRepository(final DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @PostConstruct
    void initDb() {
        jdbcClient.sql("""
                create table orders
                (
                    id    bigint not null,
                    no    varchar(255),
                    total numeric(38, 2),
                    primary key (id)
                )
                alter table if exists orders drop constraint if exists_UK_43egxxciqr9ncgmxbdx2avi8n
                alter table if exists orders add constraint exists_UK_43egxxciqr9ncgmxbdx2avi8n unique (no)
                create sequence order_SEQ start with 1 increment by 50;
                """).update();
    }

    @Override
    public void save(final Order order) {
        Long id = jdbcClient.sql("select next value for order_SEQ").query(Long.class).single();
        order.setId(id);

        jdbcClient.sql("insert into order (no, total, id) values (?,?,?)")
                .params(order.getNo(), order.getTotal(), order.getId());
    }
}

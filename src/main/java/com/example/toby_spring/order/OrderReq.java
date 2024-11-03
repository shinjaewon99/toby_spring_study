package com.example.toby_spring.order;

import java.math.BigDecimal;

public record OrderReq(String no, BigDecimal total) {
}

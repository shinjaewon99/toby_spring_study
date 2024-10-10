package com.example.toby_spring.exrate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

// json 타입의 필드를 모두 사용하지 않을경우, 필드 에러가 발생하므로
// 필요한 필드만 사용하기 위해서는 아래의 어노테이션 사용
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExRateDate(String result, Map<String, BigDecimal> rates) {
}

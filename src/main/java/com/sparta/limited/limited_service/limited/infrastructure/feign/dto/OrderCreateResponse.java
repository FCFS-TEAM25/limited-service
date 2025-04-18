package com.sparta.limited.limited_service.limited.infrastructure.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.limited.limited_service.limited.application.service.order.model.OrderType;
import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreateResponse(

    @JsonProperty("id")
    UUID orderId,
    UUID productId,
    Long userId,
    String username,
    String address,
    OrderType orderType,
    String status,
    BigDecimal price,
    Integer quantity) {
    
}

package com.sparta.limited.limited_service.limited.infrastructure.feign.dto;

import com.sparta.limited.limited_service.limited.application.service.order.model.OrderType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private UUID productId;
    private String orderType;
    private BigDecimal price;
    private Integer quantity;

    public OrderCreateRequest(UUID productId, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.orderType = OrderType.LIMITED.toString();
        this.price = price;
        this.quantity = quantity;
    }

}

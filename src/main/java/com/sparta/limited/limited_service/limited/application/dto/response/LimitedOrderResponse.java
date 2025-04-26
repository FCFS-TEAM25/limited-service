package com.sparta.limited.limited_service.limited.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedOrderResponse {

    private final UUID orderId;
    private final UUID productId;
    private final String orderType;
    private final BigDecimal price;
    private final Integer quantity;

    private LimitedOrderResponse(UUID orderId, UUID productId,
        String orderType, BigDecimal price, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
    }

    public static LimitedOrderResponse of(UUID orderId, UUID productId,
        String orderType, BigDecimal price, Integer quantity) {
        return new LimitedOrderResponse(orderId, productId, orderType, price, quantity);
    }

}

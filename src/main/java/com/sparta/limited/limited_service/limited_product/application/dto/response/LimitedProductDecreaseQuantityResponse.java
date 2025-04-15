package com.sparta.limited.limited_service.limited_product.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedProductDecreaseQuantityResponse {

    private final UUID id;
    private final UUID productId;
    private final Integer quantity;
    private final BigDecimal price;

    private LimitedProductDecreaseQuantityResponse
        (UUID id, UUID productId, Integer quantity, BigDecimal price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public static LimitedProductDecreaseQuantityResponse of(UUID id, UUID productId,
        Integer quantity, BigDecimal price) {
        return new LimitedProductDecreaseQuantityResponse(id, productId, quantity, price);
    }

}

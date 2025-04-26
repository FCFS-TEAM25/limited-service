package com.sparta.limited.limited_service.limited.application.service.limited_product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record LimitedProductInfo(

    UUID id,
    UUID productId,
    Integer quantity,
    BigDecimal price

) {

    public static LimitedProductInfo from(
        UUID id, UUID productId, Integer quantity, BigDecimal price) {
        return new LimitedProductInfo(id, productId, quantity, price);
    }
}
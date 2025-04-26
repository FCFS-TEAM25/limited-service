package com.sparta.limited.limited_service.limited_product.application.service.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfo(

    UUID productId,
    String title,
    String description,
    BigDecimal price

) {

    public static ProductInfo from(UUID productId, String title, String description,
        BigDecimal price) {
        return new ProductInfo(productId, title, description, price);
    }
}

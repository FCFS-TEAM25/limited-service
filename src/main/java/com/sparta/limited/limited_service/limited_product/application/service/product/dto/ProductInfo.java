package com.sparta.limited.limited_service.limited_product.application.service.product.dto;

import com.sparta.limited.limited_service.limited_product.infrastructure.feign.dto.ProductReadResponse;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductInfo(

    UUID productId,
    String title,
    String description,
    BigDecimal price

) {

    public static ProductInfo from(ProductReadResponse response) {
        return new ProductInfo(response.getProductId(), response.getTitle(),
            response.getDescription(), response.getPrice());
    }
}

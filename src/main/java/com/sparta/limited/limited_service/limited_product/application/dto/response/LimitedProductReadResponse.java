package com.sparta.limited.limited_service.limited_product.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedProductReadResponse {

    private final UUID id;
    private final UUID productId;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;

    private LimitedProductReadResponse(UUID id, UUID productId, String title, String description,
        BigDecimal price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public static LimitedProductReadResponse of(UUID id, UUID productId, String title,
        String description,
        BigDecimal price, Integer quantity) {
        return new LimitedProductReadResponse(id, productId, title, description, price, quantity);
    }

}

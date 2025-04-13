package com.sparta.limited.limited_service.limited_product.application.dto.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedProductUpdateResponse {

    private final UUID id;
    private final UUID productId;
    private final Integer quantity;

    private LimitedProductUpdateResponse(UUID id, UUID productId, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static LimitedProductUpdateResponse of(UUID id, UUID productId, Integer quantity) {
        return new LimitedProductUpdateResponse(id, productId, quantity);
    }

}

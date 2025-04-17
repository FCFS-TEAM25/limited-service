package com.sparta.limited.limited_service.limited_product.application.dto.request;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class LimitedProductUpdateRequest {

    private String description;
    private BigDecimal price;

}

package com.sparta.limited.limited_service.limited.application.dto.response;

import lombok.Getter;

@Getter
public class LimitedReadResponse {

    private final LimitedResponse limited;
    private final LimitedProductResponse limitedProduct;

    private LimitedReadResponse(LimitedResponse limited, LimitedProductResponse limitedProduct) {
        this.limited = limited;
        this.limitedProduct = limitedProduct;
    }

    public static LimitedReadResponse of(
        LimitedResponse limited, LimitedProductResponse limitedProduct) {

        return new LimitedReadResponse(limited, limitedProduct);
    }

}

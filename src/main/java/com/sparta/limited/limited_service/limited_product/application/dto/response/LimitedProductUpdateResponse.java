package com.sparta.limited.limited_service.limited_product.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class LimitedProductUpdateResponse {

    private final String result;
    private final String message;

    public LimitedProductUpdateResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public static LimitedProductUpdateResponse success() {
        return new LimitedProductUpdateResponse("success", "한정수량 상품 수정이 성공했습니다.");
    }

    public static LimitedProductUpdateResponse error() {
        return new LimitedProductUpdateResponse("error", "한정수량 상품 수정이 실패했습니다.");
    }
}

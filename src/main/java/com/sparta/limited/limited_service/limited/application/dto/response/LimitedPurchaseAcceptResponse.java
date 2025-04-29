package com.sparta.limited.limited_service.limited.application.dto.response;

import lombok.Getter;

@Getter
public class LimitedPurchaseAcceptResponse {

    private final String message;

    private LimitedPurchaseAcceptResponse(String message) {
        this.message = message;
    }

    public static LimitedPurchaseAcceptResponse of(String message) {
        return new LimitedPurchaseAcceptResponse(message);
    }

}

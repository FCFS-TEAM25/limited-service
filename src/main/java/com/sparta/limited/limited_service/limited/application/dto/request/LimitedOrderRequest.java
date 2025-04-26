package com.sparta.limited.limited_service.limited.application.dto.request;

import lombok.Getter;

@Getter
public class LimitedOrderRequest {

    private Integer quantity;

    protected LimitedOrderRequest() {
    }

    private LimitedOrderRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public static LimitedOrderRequest of(Integer quantity) {
        return new LimitedOrderRequest(quantity);
    }

}

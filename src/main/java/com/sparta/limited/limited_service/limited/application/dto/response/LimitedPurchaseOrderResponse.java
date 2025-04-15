package com.sparta.limited.limited_service.limited.application.dto.response;

import lombok.Getter;

@Getter
public class LimitedPurchaseOrderResponse {

    private final LimitedPurchaseResponse purchaseResponse;
    private final LimitedOrderResponse orderResponse;

    private LimitedPurchaseOrderResponse(
        LimitedPurchaseResponse purchaseResponse,
        LimitedOrderResponse orderResponse) {
        this.purchaseResponse = purchaseResponse;
        this.orderResponse = orderResponse;
    }

    public static LimitedPurchaseOrderResponse of(
        LimitedPurchaseResponse purchaseResponse,
        LimitedOrderResponse orderResponse) {
        return new LimitedPurchaseOrderResponse(purchaseResponse, orderResponse);
    }

}

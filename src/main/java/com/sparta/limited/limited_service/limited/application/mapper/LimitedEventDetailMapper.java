package com.sparta.limited.limited_service.limited.application.mapper;

import com.sparta.limited.limited_service.limited.application.dto.response.LimitedOrderResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedProductResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseOrderResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedReadResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedResponse;

public class LimitedEventDetailMapper {

    public static LimitedReadResponse toReadResponse(
        LimitedResponse limited, LimitedProductResponse limitedProduct) {

        return LimitedReadResponse.of(limited, limitedProduct);
    }

    public static LimitedPurchaseOrderResponse toPurchaseOrderResponse(
        LimitedPurchaseResponse purchaseResponse,
        LimitedOrderResponse orderResponse) {

        return LimitedPurchaseOrderResponse.of(purchaseResponse, orderResponse);
    }

}

package com.sparta.limited.limited_service.limited.application.mapper;

import com.sparta.limited.limited_service.limited.application.dto.response.LimitedOrderResponse;
import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited.application.service.order.model.OrderType;
import java.util.UUID;

public class LimitedOrderMapper {

    public static LimitedOrderResponse toOrderResponse(
        UUID orderId, LimitedProductInfo productInfo, Integer quantity) {

        return LimitedOrderResponse.of(
            orderId,
            productInfo.productId(),
            OrderType.LIMITED.toString(),
            productInfo.price(),
            quantity
        );
    }
}

package com.sparta.limited.limited_service.limited.application.mapper;

import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseResponse;
import com.sparta.limited.limited_service.limited.domain.model.LimitedPurchaseUser;

public class LimitedPurchaseMapper {

    public static LimitedPurchaseResponse toPurchaseResponse(LimitedPurchaseUser purchaseUser) {

        return LimitedPurchaseResponse.of(
            purchaseUser.getId(),
            purchaseUser.getLimitedEventId(),
            purchaseUser.getUserId());
    }

}

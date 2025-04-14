package com.sparta.limited.limited_service.limited.application.mapper;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import java.util.UUID;

public class LimitedMapper {

    public static Limited toCreateEntity(UUID limitedProductId, LimitedCreateRequest request) {
        return Limited.createOf(limitedProductId, request.getStartDate(), request.getEndDate());
    }

    public static LimitedCreateResponse toCreateResponse(Limited limited) {
        return LimitedCreateResponse.of(limited.getId(), limited.getLimitedProductId(),
            limited.getStartDate(), limited.getEndDate(), limited.getStatus());
    }

}

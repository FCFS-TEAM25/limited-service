package com.sparta.limited.limited_service.limited.application.mapper;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedListResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedProductResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedReadResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedUpdateStatusResponse;
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

    public static LimitedResponse toResponse(Limited limited) {

        return LimitedResponse.of(limited.getId(), limited.getStartDate(),
            limited.getEndDate(), limited.getStatus());
    }

    public static LimitedReadResponse toReadResponse(
        LimitedResponse limited, LimitedProductResponse limitedProduct) {

        return LimitedReadResponse.of(limited, limitedProduct);
    }

    public static LimitedListResponse toListResponse(
        Limited limited, String title) {

        return LimitedListResponse.of(limited.getId(), limited.getLimitedProductId(),
            title, limited.getStartDate(), limited.getEndDate(),
            limited.getStatus());
    }

    public static LimitedUpdateStatusResponse toUpdateStatusResponse(Limited limited) {

        return LimitedUpdateStatusResponse.of(limited.getId(), limited.getStatus(),
            limited.getStartDate(), limited.getEndDate());
    }

}

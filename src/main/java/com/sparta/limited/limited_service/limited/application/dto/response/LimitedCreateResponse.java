package com.sparta.limited.limited_service.limited.application.dto.response;

import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedCreateResponse {

    private final UUID id;
    private final UUID limitedProductId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LimitedStatus status;

    private LimitedCreateResponse(UUID id, UUID limitedProductId, LocalDateTime startDate,
        LocalDateTime endDate, LimitedStatus status) {
        this.id = id;
        this.limitedProductId = limitedProductId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static LimitedCreateResponse of(UUID id, UUID limitedProductId, LocalDateTime startDate,
        LocalDateTime endDate, LimitedStatus status) {
        return new LimitedCreateResponse(id, limitedProductId, startDate, endDate, status);
    }
}

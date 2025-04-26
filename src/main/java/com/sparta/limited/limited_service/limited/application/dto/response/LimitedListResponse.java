package com.sparta.limited.limited_service.limited.application.dto.response;

import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedListResponse {

    private final UUID id;
    private final UUID limitedProductId;
    private final String title;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LimitedStatus status;

    private LimitedListResponse(UUID id, UUID limitedProductId, String title,
        LocalDateTime startDate, LocalDateTime endDate, LimitedStatus status
    ) {
        this.id = id;
        this.limitedProductId = limitedProductId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static LimitedListResponse of(UUID id, UUID limitedProductId, String title,
        LocalDateTime startDate, LocalDateTime endDate, LimitedStatus status) {
        return new LimitedListResponse(id, limitedProductId, title, startDate, endDate, status);
    }

}

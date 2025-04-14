package com.sparta.limited.limited_service.limited.application.dto.response;

import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedUpdateStatusResponse {

    private final UUID id;
    private final LimitedStatus status;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private LimitedUpdateStatusResponse(UUID id, LimitedStatus status,
        LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static LimitedUpdateStatusResponse of(UUID id, LimitedStatus status,
        LocalDateTime startDate, LocalDateTime endDate) {
        return new LimitedUpdateStatusResponse(id, status, startDate, endDate);
    }

}

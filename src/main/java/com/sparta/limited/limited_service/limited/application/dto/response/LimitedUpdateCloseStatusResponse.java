package com.sparta.limited.limited_service.limited.application.dto.response;

import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedUpdateCloseStatusResponse {

    private final UUID id;
    private final LimitedStatus status;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private LimitedUpdateCloseStatusResponse(UUID id, LimitedStatus status,
        LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static LimitedUpdateCloseStatusResponse of(UUID id, LimitedStatus status,
        LocalDateTime startDate, LocalDateTime endDate) {
        return new LimitedUpdateCloseStatusResponse(id, status, startDate, endDate);
    }

}

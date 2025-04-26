package com.sparta.limited.limited_service.limited.application.dto.response;

import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedResponse {

    private final UUID id;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LimitedStatus status;

    private LimitedResponse(UUID id, LocalDateTime startDate,
        LocalDateTime endDate, LimitedStatus status) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static LimitedResponse of(UUID id, LocalDateTime startDate,
        LocalDateTime endDate, LimitedStatus status) {

        return new LimitedResponse(id, startDate, endDate, status);
    }

}

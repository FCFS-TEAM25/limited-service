package com.sparta.limited.limited_service.limited.application.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class LimitedCreateRequest {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}

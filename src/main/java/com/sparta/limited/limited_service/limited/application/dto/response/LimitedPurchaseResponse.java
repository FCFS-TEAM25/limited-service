package com.sparta.limited.limited_service.limited.application.dto.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class LimitedPurchaseResponse {

    private final UUID id;
    private final UUID limitedEventId;
    private final Long userId;

    private LimitedPurchaseResponse(UUID id, UUID limitedEventId, Long userId) {
        this.id = id;
        this.limitedEventId = limitedEventId;
        this.userId = userId;
    }

    public static LimitedPurchaseResponse of(UUID id, UUID limitedEventId, Long userId) {
        return new LimitedPurchaseResponse(id, limitedEventId, userId);
    }

}

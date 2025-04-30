package com.sparta.limited.limited_service.limited.application.service.dto;

import java.util.UUID;

public record PurchaseData(
    UUID limitedEventId,
    Long userId,
    Integer quantity,
    int retryCount
) {

}

package com.sparta.limited.limited_service.limited.domain.model.validator;

import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventAlreadyCloseStatusException;
import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventNotActiveException;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.util.UUID;

public class LimitedStatusValidator {

    public static void validateStatusAlreadyClose(LimitedStatus status) {
        if (status == LimitedStatus.CLOSED) {
            throw new LimitedEventAlreadyCloseStatusException();
        }
    }

    public static void validateStatusIsActive(UUID LimitedEventId, LimitedStatus status) {
        if (status != LimitedStatus.ACTIVE) {
            throw new LimitedEventNotActiveException(LimitedEventId, status);
        }
    }

}

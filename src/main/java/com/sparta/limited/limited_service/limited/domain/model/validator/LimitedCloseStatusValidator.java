package com.sparta.limited.limited_service.limited.domain.model.validator;

import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventAlreadyCloseStatusException;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;

public class LimitedCloseStatusValidator {

    public static void validateStatus(LimitedStatus status) {
        if (status == LimitedStatus.CLOSED) {
            throw new LimitedEventAlreadyCloseStatusException();
        }
    }

}

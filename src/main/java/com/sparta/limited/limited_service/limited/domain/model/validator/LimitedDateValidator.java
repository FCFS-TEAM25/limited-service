package com.sparta.limited.limited_service.limited.domain.model.validator;

import com.sparta.limited.limited_service.limited.domain.exception.InvalidEndDateException;
import com.sparta.limited.limited_service.limited.domain.exception.InvalidStartDateException;
import java.time.LocalDateTime;

public class LimitedDateValidator {

    public static void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new InvalidStartDateException(startDate);
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidEndDateException(startDate, endDate);
        }
    }

}

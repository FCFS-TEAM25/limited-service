package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.time.LocalDateTime;

public class InvalidEndDateException extends BusinessException {

    public InvalidEndDateException(LocalDateTime startDate, LocalDateTime endDate) {

        super(ErrorCode.INVALID_PARAMETER,
            "종료일은 시작일보다 이후여야 합니다. start date : " + startDate + ", end date : " + endDate);
    }
}

package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.time.LocalDateTime;

public class InvalidStartDateException extends BusinessException {

    public InvalidStartDateException(LocalDateTime startDate) {
        super(ErrorCode.INVALID_PARAMETER, "시작일은 현재보다 이후여야 합니다.  start date : " + startDate);
    }
}

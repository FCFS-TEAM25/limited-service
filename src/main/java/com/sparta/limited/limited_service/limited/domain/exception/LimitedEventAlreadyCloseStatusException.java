package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;

public class LimitedEventAlreadyCloseStatusException extends BusinessException {

    public LimitedEventAlreadyCloseStatusException() {
        super(ErrorCode.OPERATION_NOT_ALLOWED, "이미 종료된 한정판매 이벤트 입니다.");
    }
}

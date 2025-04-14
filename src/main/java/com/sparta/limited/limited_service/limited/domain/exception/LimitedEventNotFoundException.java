package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;

public class LimitedEventNotFoundException extends BusinessException {

    public LimitedEventNotFoundException(UUID limitedEventId) {
        super(ErrorCode.RESOURCES_NOT_FOUND, "존재하지 않는 한정수량 판매 이벤트입니다. id : " + limitedEventId);
    }
}

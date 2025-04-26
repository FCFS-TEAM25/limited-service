package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;

public class LimitedEventUnableChangeStatusException extends BusinessException {

    public LimitedEventUnableChangeStatusException(UUID limitedEventId) {
        super(ErrorCode.OPERATION_NOT_ALLOWED,
            "이벤트 종료 처리 실패 - 다시 시도해주세요. - id : " + limitedEventId);
    }
}

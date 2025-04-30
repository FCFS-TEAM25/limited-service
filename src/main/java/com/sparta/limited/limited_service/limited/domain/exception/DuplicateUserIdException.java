package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;

public class DuplicateUserIdException extends BusinessException {

    public DuplicateUserIdException(UUID limitedEventId) {
        super(ErrorCode.DUPLICATE_RESOURCE, "이미 구매한 사용자입니다.");
    }
}

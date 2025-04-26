package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.util.UUID;

public class LimitedEventNotActiveException extends BusinessException {

    public LimitedEventNotActiveException(UUID LimitedEventId, LimitedStatus limitedStatus) {
        super(ErrorCode.OPERATION_NOT_ALLOWED,
            "진행중인 이벤트가 아닙니다. Id : " + LimitedEventId + ",  status : " + limitedStatus);
    }
}

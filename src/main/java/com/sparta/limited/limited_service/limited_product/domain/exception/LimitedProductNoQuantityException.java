package com.sparta.limited.limited_service.limited_product.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;

public class LimitedProductNoQuantityException extends BusinessException {

    public LimitedProductNoQuantityException(UUID limitedProductId) {
        super(ErrorCode.OPERATION_NOT_ALLOWED,
            "The limited product is out of quantity - id : " + limitedProductId);
    }
}

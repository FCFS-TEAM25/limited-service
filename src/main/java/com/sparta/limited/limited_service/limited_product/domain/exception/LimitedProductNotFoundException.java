package com.sparta.limited.limited_service.limited_product.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;
import java.util.UUID;

public class LimitedProductNotFoundException extends BusinessException {

    public LimitedProductNotFoundException(UUID limitedProductId) {
        super(ErrorCode.RESOURCES_NOT_FOUND,
            "Limited Product Not Found - id : " + limitedProductId);
    }
}

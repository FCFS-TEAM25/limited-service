package com.sparta.limited.limited_service.limited.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;

public class OrderServiceFeignClientException extends BusinessException {

    public OrderServiceFeignClientException() {
        super(ErrorCode.INTERNAL_SERVER, "Order Servie : 주문이 실패했습니다");
    }
}

package com.sparta.limited.limited_service.limited_product.domain.exception;

import com.sparta.limited.common_module.exception.BusinessException;
import com.sparta.limited.common_module.exception.ErrorCode;

public class ProductFeignClientException extends BusinessException {

    public ProductFeignClientException() {
        super(ErrorCode.INTERNAL_SERVER, "Product Service Feign Client Error - 상품 정보를 가져올 수 없습니다.");
    }
}

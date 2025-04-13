package com.sparta.limited.limited_service.limited_product.application.mapper;

import com.sparta.limited.limited_service.limited_product.application.dto.request.LimitedProductCreateRequest;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductCreateResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductReadResponse;
import com.sparta.limited.limited_service.limited_product.application.service.product.dto.ProductInfo;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;

public class LimitedProductMapper {

    public static LimitedProduct toCreateEntity(
        ProductInfo productInfo,
        LimitedProductCreateRequest request) {

        return LimitedProduct.of(productInfo.productId(), productInfo.title(),
            productInfo.description(), productInfo.price(),
            request.getQuantity());
    }

    public static LimitedProductCreateResponse toCreateResponse(LimitedProduct limitedProduct) {
        return LimitedProductCreateResponse.of(limitedProduct.getId(),
            limitedProduct.getProductId(), limitedProduct.getTitle(),
            limitedProduct.getDescription(), limitedProduct.getPrice(),
            limitedProduct.getQuantity());
    }

    public static LimitedProductReadResponse toReadResponse(LimitedProduct limitedProduct) {
        return LimitedProductReadResponse.of(limitedProduct.getId(),
            limitedProduct.getProductId(), limitedProduct.getTitle(),
            limitedProduct.getDescription(), limitedProduct.getPrice(),
            limitedProduct.getQuantity());
    }

}

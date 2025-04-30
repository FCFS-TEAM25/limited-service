package com.sparta.limited.limited_service.limited.application.service.limited_product;

import com.sparta.limited.limited_service.limited.application.dto.response.LimitedProductResponse;
import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductDecreaseQuantityResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductReadResponse;
import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitedProductFacade {

    private final LimitedProductService limitedProductService;

    public LimitedProductInfo decreaseLimitedProductQuantity(Integer orderQuantity,
        UUID limitedProductId) {
        LimitedProductDecreaseQuantityResponse response = limitedProductService.decreaseQuantity(
            orderQuantity, limitedProductId);
        return LimitedProductInfo.from(response.getId(), response.getProductId(),
            response.getQuantity(), response.getPrice());
    }

    public LimitedProductResponse getLimitedProduct(UUID limitedProductId) {
        LimitedProductReadResponse response = limitedProductService.getLimitedProduct(
            limitedProductId);
        return LimitedProductResponse.of(response.getId(), response.getProductId(),
            response.getTitle(),
            response.getDescription(), response.getPrice(), response.getQuantity());
    }

    public LimitedProductInfo getLimitedProductInfo(UUID limitedProductId) {
        LimitedProductReadResponse response = limitedProductService.getLimitedProduct(
            limitedProductId);
        return LimitedProductInfo.from(response.getId(), response.getProductId(),
            response.getQuantity(), response.getPrice());
    }

}

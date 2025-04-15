package com.sparta.limited.limited_service.limited.application.service.limited_product;

import com.sparta.limited.limited_service.limited.application.dto.response.LimitedProductResponse;
import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductDecreaseQuantityResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductReadResponse;
import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitedProductFacade {

    private final LimitedProductService limitedProductService;

    public LimitedProductResponse getLimitedProduct(UUID limitedProductId) {
        LimitedProductReadResponse response = limitedProductService.getLimitedProduct(
            limitedProductId);
        return LimitedProductResponse.of(response.getId(), response.getProductId(),
            response.getTitle(),
            response.getDescription(), response.getPrice(), response.getQuantity());
    }

    public Map<UUID, String> getLimitedProductTitles(List<UUID> limitedProductIds) {

        Map<UUID, LimitedProduct> map = limitedProductService.getLimitedProductsByids(
            limitedProductIds);

        return map.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().getTitle()
            ));
    }

    public LimitedProductInfo decreaseQuantity(UUID limitedProductId) {
        LimitedProductDecreaseQuantityResponse response = limitedProductService.decreaseQuantity(
            limitedProductId);

        return LimitedProductInfo.from(response.getId(),
            response.getProductId(), response.getQuantity(), response.getPrice());
    }

}

package com.sparta.limited.limited_service.limited_product.application.service;

import com.sparta.limited.limited_service.limited_product.application.dto.request.LimitedProductCreateRequest;
import com.sparta.limited.limited_service.limited_product.application.dto.request.LimitedProductUpdateRequest;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductCreateResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductDecreaseQuantityResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductReadResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductUpdateResponse;
import com.sparta.limited.limited_service.limited_product.application.mapper.LimitedProductMapper;
import com.sparta.limited.limited_service.limited_product.application.service.product.ProductClientService;
import com.sparta.limited.limited_service.limited_product.application.service.product.dto.ProductInfo;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LimitedProductService {

    private final LimitedProductRepository limitedProductRepository;
    private final ProductClientService productService;

    @Transactional
    public LimitedProductCreateResponse createLimitedProduct(UUID productId,
        LimitedProductCreateRequest request) {

        ProductInfo productInfo = productService.getProduct(productId);

        LimitedProduct limitedProduct = LimitedProductMapper.toCreateEntity(productInfo, request);

        limitedProductRepository.save(limitedProduct);
        return LimitedProductMapper.toCreateResponse(limitedProduct);
    }

    @Transactional(readOnly = true)
    public LimitedProductReadResponse getLimitedProduct(UUID limitedProductId) {

        LimitedProduct limitedProduct = limitedProductRepository.findById(limitedProductId);
        return LimitedProductMapper.toReadResponse(limitedProduct);
    }

    @Transactional
    public LimitedProductDecreaseQuantityResponse decreaseQuantity(
        UUID limitedProductId) {

        LimitedProduct limitedProduct = limitedProductRepository.findByIdWithLock(limitedProductId);

        limitedProduct.decreaseQuantity();

        return LimitedProductMapper.toDecreaseQuantityResponse(limitedProduct);
    }


    @Transactional
    public LimitedProductUpdateResponse updateLimitedProduct(UUID productId,
        LimitedProductUpdateRequest request) {
        try {
            LimitedProduct limitedProduct = limitedProductRepository.findByProductId(productId);
            limitedProduct.updateLimitedProduct(request.getDescription(), request.getPrice());
            return LimitedProductUpdateResponse.success();
        } catch (Exception e) {
            return LimitedProductUpdateResponse.error();
        }
    }
}

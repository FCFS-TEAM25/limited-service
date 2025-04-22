package com.sparta.limited.limited_service.limited_product.application.service.product;

import com.sparta.limited.limited_service.limited_product.application.service.product.dto.ProductInfo;
import com.sparta.limited.limited_service.limited_product.domain.exception.ProductFeignClientException;
import com.sparta.limited.limited_service.limited_product.infrastructure.feign.ProductFeignClient;
import com.sparta.limited.limited_service.limited_product.infrastructure.feign.dto.ProductReadResponse;
import feign.FeignException.FeignClientException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "feign client : call product-service")
@RequiredArgsConstructor
public class ProductClientService {

    private final ProductFeignClient productFeignClient;

    public ProductInfo getProduct(UUID productId) {
        try {
            ProductReadResponse response = productFeignClient.getProduct(productId);
            return ProductInfo.from(response.getProductId(), response.getTitle(),
                response.getDescription(), response.getPrice());
        } catch (FeignClientException e) {
            throw new ProductFeignClientException();
        }
    }

}

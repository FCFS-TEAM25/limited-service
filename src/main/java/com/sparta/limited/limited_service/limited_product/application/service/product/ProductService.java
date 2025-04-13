package com.sparta.limited.limited_service.limited_product.application.service.product;

import com.sparta.limited.limited_service.limited_product.application.service.product.dto.ProductInfo;
import com.sparta.limited.limited_service.limited_product.infrastructure.feign.ProductFeignClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "feign client : call product-service")
@RequiredArgsConstructor
public class ProductService {

    private final ProductFeignClient productFeignClient;

    public ProductInfo getProduct(UUID productId) {
        return ProductInfo.from(productFeignClient.getProduct(productId));
    }

}

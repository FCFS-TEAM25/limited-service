package com.sparta.limited.limited_service.limited_product.infrastructure.feign;

import com.sparta.limited.limited_service.limited_product.infrastructure.feign.dto.ProductReadResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/v1/internal/products/{productId}")
    ProductReadResponse getProduct(@PathVariable UUID productId);

}

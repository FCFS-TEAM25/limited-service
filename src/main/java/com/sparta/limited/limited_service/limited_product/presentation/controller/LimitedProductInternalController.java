package com.sparta.limited.limited_service.limited_product.presentation.controller;

import com.sparta.limited.limited_service.limited_product.application.dto.request.LimitedProductUpdateRequest;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductUpdateResponse;
import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/limited-products")
public class LimitedProductInternalController {

    private final LimitedProductService limitedProductService;

    @PutMapping("/{productId}/update-product-info")
    public ResponseEntity<LimitedProductUpdateResponse> updateLimitedProductInfo(
        @PathVariable("productId") UUID productId,
        @RequestBody LimitedProductUpdateRequest request) {

        LimitedProductUpdateResponse response = limitedProductService.updateLimitedProduct(
            productId, request);
        return ResponseEntity.ok(response);
    }
}

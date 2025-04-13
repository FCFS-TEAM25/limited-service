package com.sparta.limited.limited_service.limited_product.presentation.controller;

import com.sparta.limited.limited_service.limited_product.application.dto.request.LimitedProductCreateRequest;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductCreateResponse;
import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/limited-products")
public class LimitedProductController {

    private final LimitedProductService limitedProductService;

    @PostMapping("/{productId}")
    public ResponseEntity<LimitedProductCreateResponse> createLimitedProduct(
        @PathVariable("productId") UUID productId,
        @RequestBody LimitedProductCreateRequest request) {

        LimitedProductCreateResponse response = limitedProductService.createLimitedProduct(
            productId, request);
        return ResponseEntity.ok(response);
    }

}

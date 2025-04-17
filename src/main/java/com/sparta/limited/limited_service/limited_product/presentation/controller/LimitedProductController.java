package com.sparta.limited.limited_service.limited_product.presentation.controller;

import com.sparta.limited.common_module.common.aop.RoleCheck;
import com.sparta.limited.limited_service.limited_product.application.dto.request.LimitedProductCreateRequest;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductCreateResponse;
import com.sparta.limited.limited_service.limited_product.application.dto.response.LimitedProductReadResponse;
import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/limited-products")
public class LimitedProductController {

    private final LimitedProductService limitedProductService;

    @RoleCheck("ROLE_ADMIN")
    @PostMapping("/{productId}")
    public ResponseEntity<LimitedProductCreateResponse> createLimitedProduct(
        @RequestHeader("X-User-Id") Long userId,
        @PathVariable("productId") UUID productId,
        @RequestBody LimitedProductCreateRequest request) {

        LimitedProductCreateResponse response = limitedProductService.createLimitedProduct(
            productId, request);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/limited-products/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{limitedProductId}")
    public ResponseEntity<LimitedProductReadResponse> getLimitedProduct(
        @PathVariable("limitedProductId") UUID limitedProductId) {

        LimitedProductReadResponse response = limitedProductService.getLimitedProduct(
            limitedProductId);
        return ResponseEntity.ok(response);
    }

}

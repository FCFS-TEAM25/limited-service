package com.sparta.limited.limited_service.limited.presentation.controller;

import com.sparta.limited.common_module.common.annotation.CurrentUserId;
import com.sparta.limited.common_module.common.aop.RoleCheck;
import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.request.LimitedOrderRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedListResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseAcceptResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedReadResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedUpdateStatusResponse;
import com.sparta.limited.limited_service.limited.application.service.LimitedService;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/limited-events")
public class LimitedController {

    private final LimitedService limitedService;

    @RoleCheck("ROLE_ADMIN")
    @PostMapping("/{limitedProductId}")
    public ResponseEntity<LimitedCreateResponse> createLimitedEvent(
        @CurrentUserId Long userId,
        @PathVariable UUID limitedProductId,
        @RequestBody LimitedCreateRequest request) {

        LimitedCreateResponse response = limitedService.createLimitedEvent(limitedProductId,
            request);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/limited-events/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{limitedEventId}")
    public ResponseEntity<LimitedReadResponse> getLimitedEvent(
        @PathVariable UUID limitedEventId
    ) {

        LimitedReadResponse response = limitedService.getLimitedEvent(limitedEventId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<LimitedListResponse>> getLimitedEvents(
        @PageableDefault() Pageable pageable) {

        Page<LimitedListResponse> responses = limitedService.getLimitedEvents(pageable);
        return ResponseEntity.ok(responses);
    }

    @RoleCheck("ROLE_ADMIN")
    @PatchMapping("/{limitedEventId}/status-close")
    public ResponseEntity<LimitedUpdateStatusResponse> closeLimitedEvent(
        @CurrentUserId Long userId,
        @PathVariable UUID limitedEventId) {

        LimitedUpdateStatusResponse response = limitedService.updateStatusClose(
            limitedEventId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{limitedEventId}/purchase")
    public ResponseEntity<LimitedPurchaseAcceptResponse> purchaseOrder(
        @CurrentUserId Long userId,
        @PathVariable UUID limitedEventId,
        @RequestBody LimitedOrderRequest limitedOrderRequest) {

        LimitedPurchaseAcceptResponse response = limitedService.purchaseOrderRedis(userId,
            limitedEventId, limitedOrderRequest);
        return ResponseEntity.ok(response);
    }


}

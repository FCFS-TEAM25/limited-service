package com.sparta.limited.limited_service.limited.presentation.controller;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedReadResponse;
import com.sparta.limited.limited_service.limited.application.service.LimitedService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/limited-events")
public class LimitedController {

    private final LimitedService limitedService;

    @PostMapping("/{limitedProductId}")
    public ResponseEntity<LimitedCreateResponse> createLimitedEvent(
        @PathVariable UUID limitedProductId,
        @RequestBody LimitedCreateRequest request) {

        LimitedCreateResponse response = limitedService.createLimitedEvent(limitedProductId,
            request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{limitedEventId}")
    public ResponseEntity<LimitedReadResponse> getLimitedEvent(
        @PathVariable UUID limitedEventId
    ) {

        LimitedReadResponse response = limitedService.getLimitedEvent(limitedEventId);
        return ResponseEntity.ok(response);
    }


}

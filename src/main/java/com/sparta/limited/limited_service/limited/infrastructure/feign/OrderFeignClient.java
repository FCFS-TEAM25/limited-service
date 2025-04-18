package com.sparta.limited.limited_service.limited.infrastructure.feign;

import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateRequest;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", url = "localhost:19094/api/v1/internal/orders")
public interface OrderFeignClient {

    @PostMapping
    OrderCreateResponse createOrder(
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody OrderCreateRequest request);

}

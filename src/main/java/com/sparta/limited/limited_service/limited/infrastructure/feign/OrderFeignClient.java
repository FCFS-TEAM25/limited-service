package com.sparta.limited.limited_service.limited.infrastructure.feign;

import com.sparta.limited.common_module.common.annotation.CurrentUserId;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateRequest;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @PostMapping("/api/v1/internal/orders")
    OrderCreateResponse createOrder(
        @CurrentUserId Long userId,
        @RequestBody OrderCreateRequest request);

}

package com.sparta.limited.limited_service.limited.application.service.order;

import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited.domain.exception.OrderServiceFeignClientException;
import com.sparta.limited.limited_service.limited.infrastructure.feign.OrderFeignClient;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateRequest;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateResponse;
import com.sparta.limited.limited_service.limited.infrastructure.redis.RedisService;
import feign.FeignException.FeignClientException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderClientService {

    private final OrderFeignClient orderFeignClient;
    private final RedisService redisService;

    public UUID createOrder(UUID limitedEventId, LocalDateTime endDate,
        Long userId, LimitedProductInfo productInfo, Integer quantity) {

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(productInfo.productId(),
            productInfo.price(), quantity);

        try {
            OrderCreateResponse response = orderFeignClient.createOrder(userId, orderCreateRequest);
            redisService.updatePurchaseConfirmed(limitedEventId, userId, endDate);
            return response.orderId();
        } catch (FeignClientException e) {
            log.warn("Order service failed. limitedId={}, userId={}", limitedEventId, userId, e);

            redisService.increaseQuantity(limitedEventId, quantity);
            throw new OrderServiceFeignClientException();
        }
    }

}


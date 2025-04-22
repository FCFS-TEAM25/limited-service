package com.sparta.limited.limited_service.limited.application.service.order;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedOrderRequest;
import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited.domain.exception.OrderServiceFeignClientException;
import com.sparta.limited.limited_service.limited.infrastructure.feign.OrderFeignClient;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateRequest;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateResponse;
import feign.FeignException.FeignClientException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderClientService {

    private final OrderFeignClient orderFeignClient;

    public UUID createOrder(Long userId, LimitedProductInfo productInfo,
        LimitedOrderRequest orderRequest) {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(productInfo.productId(),
            productInfo.price(), orderRequest.getQuantity());

        try {
            OrderCreateResponse response = orderFeignClient.createOrder(userId, orderCreateRequest);
            return response.orderId();
        } catch (FeignClientException e) {
            throw new OrderServiceFeignClientException();
        }
    }

}

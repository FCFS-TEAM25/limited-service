package com.sparta.limited.limited_service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedOrderRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseOrderResponse;
import com.sparta.limited.limited_service.limited.application.service.LimitedService;
import com.sparta.limited.limited_service.limited.application.service.limited_product.LimitedProductFacade;
import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited.application.service.order.OrderClientService;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.LimitedPurchaseUser;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedPurchaseRepository;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("LimitedService - 단위 테스트")
public class LimitedServiceTest {

    @Mock
    private LimitedProductFacade limitedProductFacade;

    @Mock
    private OrderClientService orderClientService;

    @Mock
    private LimitedRepository limitedRepository;


    @Mock
    private LimitedPurchaseRepository limitedPurchaseRepository;

    @InjectMocks
    private LimitedService limitedService;

    @Test
    @DisplayName("한정판매 구매")
    void purchaseOrder() {

        Long userId = 1L;
        UUID limitedProductId = UUID.randomUUID();
        UUID limitedEventId = UUID.randomUUID();
        LimitedOrderRequest limitedOrderRequest = LimitedOrderRequest.of(1);

        Limited limited = Limited.createOf(
            limitedProductId,
            LocalDateTime.now().plusSeconds(1),
            LocalDateTime.now().plusDays(1));
        limited.updateStatusActive();

        ReflectionTestUtils.setField(limited, "id", limitedEventId);

        given(limitedRepository.findById(limitedEventId)).willReturn(limited);

        LimitedProductInfo limitedProductInfo = LimitedProductInfo.from(
            UUID.randomUUID(),
            UUID.randomUUID(),
            1,
            BigDecimal.valueOf(10000)
        );
        given(limitedProductFacade.decreaseQuantity(limitedProductId)).willReturn(
            limitedProductInfo);

        UUID orderId = UUID.randomUUID();
        given(orderClientService.createOrder(userId, limitedProductInfo,
            limitedOrderRequest)).willReturn(orderId);

        // when
        LimitedPurchaseOrderResponse result = limitedService.purchaseOrder(userId, limitedEventId,
            limitedOrderRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getOrderResponse().getOrderId()).isEqualTo(orderId);

        ArgumentCaptor<LimitedPurchaseUser> captor = ArgumentCaptor.forClass(
            LimitedPurchaseUser.class);
        verify(limitedPurchaseRepository).save(captor.capture());

    }

}

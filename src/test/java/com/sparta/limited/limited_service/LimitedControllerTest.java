package com.sparta.limited.limited_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.limited.limited_service.limited.application.dto.request.LimitedOrderRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedOrderResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseOrderResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseResponse;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedEventDetailMapper;
import com.sparta.limited.limited_service.limited.application.service.LimitedService;
import com.sparta.limited.limited_service.limited.application.service.order.model.OrderType;
import com.sparta.limited.limited_service.limited.presentation.controller.LimitedController;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@ActiveProfiles("test")
@WebMvcTest(controllers = LimitedController.class, excludeAutoConfiguration = {
    AopAutoConfiguration.class})

@DisplayName("Controller 슬라이스 테스트")
public class LimitedControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LimitedService limitedService;

    @Test
    @DisplayName("한정판매 구매 생성")
    void purchaseOrder() throws Exception {

        // given
        UUID limitedEventId = UUID.randomUUID();
        Long userId = 1L;
        LimitedOrderRequest limitedOrderRequest = LimitedOrderRequest.of(1);

        LimitedOrderResponse limitedOrderResponse = LimitedOrderResponse.of(
            UUID.randomUUID(),
            UUID.randomUUID(),
            OrderType.LIMITED.toString(),
            new BigDecimal("10000"),
            limitedOrderRequest.getQuantity()
        );

        LimitedPurchaseOrderResponse response = LimitedEventDetailMapper.toPurchaseOrderResponse(
            LimitedPurchaseResponse.of(UUID.randomUUID(), limitedEventId, userId),
            limitedOrderResponse
        );

        given(limitedService.purchaseOrder(eq(userId), eq(limitedEventId), any()))
            .willReturn(response);

        // when & then
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/limited-events/{limitedEventId}/purchase", limitedEventId)
                    .header("X-user-id", 1L)
                    .content(objectMapper.writeValueAsString(limitedOrderRequest))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isCreated());


    }
}

package com.sparta.limited.limited_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedOrderRequest;
import com.sparta.limited.limited_service.limited.application.service.LimitedService;
import com.sparta.limited.limited_service.limited.application.service.order.model.OrderType;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import com.sparta.limited.limited_service.limited.infrastructure.feign.OrderFeignClient;
import com.sparta.limited.limited_service.limited.infrastructure.feign.dto.OrderCreateResponse;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LimitedPurchaseTest {


    private static final int TOTAL_QUANTITY = 100;
    private static final int NUM_THREADS = 9000;
    private static final int THREAD_POOL_SIZE = 6000;

    @MockitoBean
    private OrderFeignClient orderFeignClient;

    @Autowired
    private LimitedService limitedService;
    @Autowired
    private LimitedRepository limitedRepository;
    @Autowired
    private LimitedProductRepository limitedProductRepository;


    @DisplayName("한정판매 구매 테스트")
    @Test
    void testMultiThreadPurchase() throws InterruptedException {

        LimitedProduct limitedProduct = LimitedProduct.of(
            UUID.randomUUID(),
            "상품",
            "상품설명",
            BigDecimal.valueOf(100000),
            TOTAL_QUANTITY
        );

        limitedProductRepository.save(limitedProduct);

        Limited limited = Limited.createOf(limitedProduct.getId(),
            LocalDateTime.now().plusSeconds(1), LocalDateTime.now().plusDays(1));
        limitedRepository.save(limited);
        limited.updateStatusActive(); // test 시 스케줄링 비활성화
        limitedRepository.save(limited);

        given(orderFeignClient.createOrder(anyLong(), any()))
            .willReturn(new OrderCreateResponse(
                UUID.randomUUID(),
                UUID.randomUUID(),
                1234L,
                "kim",
                "address",
                OrderType.LIMITED,
                "PENDING",
                BigDecimal.valueOf(10000),
                1
            ));

        LimitedOrderRequest limitedOrderRequest = LimitedOrderRequest.of(1);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_THREADS);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {

            executorService.submit(() -> {
                try {
                    startLatch.await();
                    limitedService.purchaseOrder((long) 1234, limited.getId(), limitedOrderRequest);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        endLatch.await();
        executorService.shutdown(); // 쓰레드풀 종료!!

        long totalRunTime = System.currentTimeMillis() - startTime;

        LimitedProduct result = limitedProductRepository.findById(limitedProduct.getId());

        System.out.println("테스트 시간 : " + totalRunTime + "ms");
        System.out.println("시작 재고 : " + TOTAL_QUANTITY);
        System.out.println("성공 개수 : " + successCount.intValue());
        System.out.println("실패 개수 :  " + failCount.intValue());
        System.out.println("최종 재고 : " + result.getQuantity());

        assertEquals(result.getQuantity(), TOTAL_QUANTITY - successCount.get());

    }


}

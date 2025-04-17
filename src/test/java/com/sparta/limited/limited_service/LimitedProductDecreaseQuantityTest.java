package com.sparta.limited.limited_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import java.math.BigDecimal;
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

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LimitedProductDecreaseQuantityTest {

    private static final int TOTAL_QUANTITY = 100;
    private static final int NUM_THREADS = 10000;
    private static final int THREAD_POOL_SIZE = 128;

    @Autowired
    private LimitedProductRepository limitedProductRepository;
    @Autowired
    private LimitedProductService limitedProductService;

    @DisplayName("재고 조회 및 감소 정합성 테스트")
    @Test
    void testMultiThreadDecrease() throws InterruptedException {

        LimitedProduct limitedProduct = LimitedProduct.of(
            UUID.randomUUID(),
            "상품",
            "상품설명",
            BigDecimal.valueOf(100000),
            TOTAL_QUANTITY
        );
        limitedProductRepository.save(limitedProduct);
        UUID limitedProductId = limitedProduct.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CountDownLatch countDownLatch = new CountDownLatch(NUM_THREADS);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executorService.submit(() -> {
                try {
                    limitedProductService.decreaseQuantity(limitedProductId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown(); // 쓰레드풀 종료!!

        long totalRunTime = System.currentTimeMillis() - startTime;

        LimitedProduct result = limitedProductRepository.findById(limitedProductId);

        System.out.println("테스트 시간 : " + totalRunTime + "ms");
        System.out.println("시작 재고 : " + TOTAL_QUANTITY);
        System.out.println("성공 개수 : " + successCount.intValue());
        System.out.println("실패 개수 :  " + failCount.intValue());
        System.out.println("최종 재고 : " + result.getQuantity());

        assertEquals(0, result.getQuantity());
        assertEquals(result.getQuantity(), TOTAL_QUANTITY - successCount.get());
    }

}

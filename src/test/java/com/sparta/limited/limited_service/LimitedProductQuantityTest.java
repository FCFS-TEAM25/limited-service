package com.sparta.limited.limited_service;

import com.sparta.limited.limited_service.limited_product.application.service.LimitedProductService;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class LimitedProductQuantityTest {

    private static final Logger logger = LoggerFactory.getLogger(LimitedProductQuantityTest.class);

    @Autowired
    private LimitedProductRepository limitedProductRepository;

    @Autowired
    private LimitedProductService limitedProductService;

    @DisplayName("잘못된 통합테스트 코드 ")
    @Test
    public void testLimitedProductQuantityLocking() throws InterruptedException {
        logger.info("초기 상품 설정");
        LimitedProduct product1 = LimitedProduct.of(UUID.randomUUID(), "상품명1", "상품 설명1",
            BigDecimal.valueOf(10000), 10);
        LimitedProduct product2 = LimitedProduct.of(UUID.randomUUID(), "상품명2", "상품 설명2",
            BigDecimal.valueOf(20000), 1);

        limitedProductRepository.save(product1);
        limitedProductRepository.save(product2);

        // 재고가 10개 남은 상황에서의 테스트 -> 둘 다 재고 감소 가능
        Thread thread1 = new Thread(() -> {
            logger.info("스레드 1 : 재고 감소 시도");
            limitedProductService.decreaseQuantity(product1.getId());
            logger.info("스레드 1 : 재고 감소 완료");
        });

        Thread thread2 = new Thread(() -> {
            logger.info("스레드 2 : 재고 감소 시도");
            limitedProductService.getLimitedProduct(product1.getId());
            logger.info("스레드 2 : 재고 감소 완료");
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // 재고가 한 개 남은 상황에서의 테스트 -> 뒤에 들어온 스레드는 재고 감소 불가능
        Thread thread3 = new Thread(() -> {
            logger.info("스레드 3 : 재고 감소 시도");
            limitedProductService.decreaseQuantity(product2.getId());
            logger.info("스레드 3 : 재고 감소 완료");
        });

        Thread thread4 = new Thread(() -> {
            logger.info("스레드 4 : 재고 감소 시도");
            limitedProductService.decreaseQuantity(product2.getId());
            logger.info("스레드 4 : 재고 감소 완료");
        });

        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();
    }


}

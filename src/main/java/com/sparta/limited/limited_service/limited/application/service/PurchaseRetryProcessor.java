package com.sparta.limited.limited_service.limited.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.limited.limited_service.limited.application.service.dto.PurchaseData;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class PurchaseRetryProcessor {

    private final LimitedService limitedService;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    @Scheduled(fixedDelay = 10000)
    public void retryPurchasesFail() {
        try {
            List<String> failedPurchases = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                String popped = redisTemplate.opsForList().leftPop("purchase_fail_list");
                if (popped == null) {
                    break;
                }
                failedPurchases.add(popped);
            }

            for (String purchase : failedPurchases) {
                PurchaseData data = objectMapper.readValue(purchase, PurchaseData.class);

                try {
                    limitedService.purchaseOrderSchedule(data.userId(),
                        data.limitedEventId(), data.quantity());

                } catch (Exception e) {
                    log.error("구매 재처리 실패: {}", purchase, e);

                    if (data.retryCount() >= 5) {
                        log.error("구매 재처리 5회 실패 : {}", purchase, e);
                        String retryFailJson = objectMapper.writeValueAsString(data);
                        redisTemplate.opsForList()
                            .rightPush("purchase_retry_fail_list", retryFailJson);
                    } else {
                        PurchaseData retryData = new PurchaseData(
                            data.limitedEventId(),
                            data.userId(),
                            data.quantity(),
                            data.retryCount() + 1
                        );
                        String retryJson = objectMapper.writeValueAsString(retryData);
                        redisTemplate.opsForList().rightPush("purchase_fail_list", retryJson);
                    }
                }
            }
        } catch (Exception e) {
            log.error("구매 재처리 스케줄러 전체 실패", e);
        }
    }

}

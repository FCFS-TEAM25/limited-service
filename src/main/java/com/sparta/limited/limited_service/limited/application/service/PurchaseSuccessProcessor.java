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
public class PurchaseSuccessProcessor {

    private final LimitedService limitedService;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    @Scheduled(fixedDelay = 5000)
    public void processPurchasesSuccess() {

        try {
            List<String> purchases = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                String popped = redisTemplate.opsForList().leftPop("purchase_success_list");
                if (popped == null) {
                    break;
                }
                purchases.add(popped);
            }

            for (String purchase : purchases) {
                PurchaseData data = objectMapper.readValue(purchase, PurchaseData.class);
                try {
                    limitedService.purchaseOrderSchedule(data.userId(),
                        data.limitedEventId(),
                        data.quantity());

                } catch (Exception e) {
                    log.error("구매 실패 : {}", purchase, e);

                    PurchaseData retryData = new PurchaseData(
                        data.limitedEventId(),
                        data.userId(),
                        data.quantity(),
                        1
                    );
                    String retryJson = objectMapper.writeValueAsString(retryData);
                    redisTemplate.opsForList().rightPush("purchase_fail_list", retryJson);
                }
            }
        } catch (Exception e) {
            log.error("구매 스케줄러 전체 실패", e);
        }
    }
}

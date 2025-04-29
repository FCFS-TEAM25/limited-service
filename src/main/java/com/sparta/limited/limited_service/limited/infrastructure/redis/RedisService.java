package com.sparta.limited.limited_service.limited.infrastructure.redis;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;


    // 초기 이벤트 상태 세팅
    public void generateLimitedEventStatus(UUID limitedEventId, LocalDateTime endDate) {
        String key = "limited_event_status:" + limitedEventId;
        stringRedisTemplate.opsForValue()
            .setIfAbsent(key, "1", Duration.ofSeconds(setTtlSeconds(endDate)));
    }

    // 초기 재고 세팅
    public void generateInitQuantity(UUID limitedEventId, Integer quantity, LocalDateTime endDate) {
        String key = "limited_quantity:" + limitedEventId;
        stringRedisTemplate.opsForValue()
            .setIfAbsent(key, String.valueOf(quantity), Duration.ofSeconds(setTtlSeconds(endDate)));
    }

    // 주문 완료 후 중복 구매 기록 value 수정
    public void updatePurchaseConfirmed(UUID limitedEventId, Long userId, LocalDateTime endDate) {
        String key = "limited_purchase:" + limitedEventId + ":" + userId;

        stringRedisTemplate.opsForValue().set(key, "CONFIRMED", setTtlSeconds(endDate));
    }

    // 주문 실패시 재고 복구
    public void increaseQuantity(UUID limitedEventId, Integer quantity) {
        String key = "limited_quantity:" + limitedEventId;
        stringRedisTemplate.opsForValue().increment(key, quantity);

    }

    public <T> T executeLuaScript(DefaultRedisScript<T> script, List<String> key, Object... args) {
        return stringRedisTemplate.execute(script, key, args);
    }

    private Long setTtlSeconds(LocalDateTime date) {
        return Math.max(0, Duration.between(LocalDateTime.now(), date).toSeconds());
    }

}

package com.sparta.limited.limited_service.limited.application.service;

import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import jakarta.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "status update scheduler")
public class LimitedEventScheduler {

    private final LimitedRepository limitedRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateStatusScheduled() {
        log.info("스케줄러 실행 - 이벤트 상태 자동 업데이트");

        List<Limited> toStart = limitedRepository.findByStatusAndStartDateBefore(
            LimitedStatus.PENDING, LocalDateTime.now());
        toStart.forEach(limited -> {
            try {
                limited.updateStatusActive();
            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.warn("ACTIVE 로 상태 변경 실패 (낙관적 락) - id : {}, 에러메세지 : {}",
                    limited.getId(), e.getMessage());
            }
        });

        List<Limited> toEnd = limitedRepository.findByStatusAndEndDateBefore(
            LimitedStatus.ACTIVE, LocalDateTime.now());
        toEnd.forEach(limited -> {
            try {
                limited.updateStatusClose();
            } catch (OptimisticLockException | StaleObjectStateException e) {
                log.warn("CLOSE 로 상태 변경 실패 (낙관적 락) - id : {}, 에러메세지 : {}",
                    limited.getId(), e.getMessage());
            }
        });
    }
}

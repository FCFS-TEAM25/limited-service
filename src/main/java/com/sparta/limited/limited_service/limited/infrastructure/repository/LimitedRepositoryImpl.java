package com.sparta.limited.limited_service.limited.infrastructure.repository;

import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventNotFoundException;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import com.sparta.limited.limited_service.limited.infrastructure.persistence.JpaLimitedRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LimitedRepositoryImpl implements LimitedRepository {

    private final JpaLimitedRepository jpaLimitedRepository;

    @Override
    public void save(Limited limited) {
        jpaLimitedRepository.save(limited);
    }

    @Override
    public Limited findById(UUID limitedEventId) {
        return jpaLimitedRepository.findById(limitedEventId)
            .orElseThrow(() -> new LimitedEventNotFoundException(limitedEventId));
    }

    @Override
    public List<Limited> findAll() {
        return jpaLimitedRepository.findAll();
    }

    @Override
    public List<Limited> findByStatusAndStartDateBefore(LimitedStatus status, LocalDateTime now) {
        return jpaLimitedRepository.findByStatusAndStartDateBefore(status, now);
    }

    @Override
    public List<Limited> findByStatusAndEndDateBefore(LimitedStatus status, LocalDateTime now) {
        return jpaLimitedRepository.findByStatusAndEndDateBefore(status, now);
    }
}

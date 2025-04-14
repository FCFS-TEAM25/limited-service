package com.sparta.limited.limited_service.limited.infrastructure.repository;

import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventNotFoundException;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import com.sparta.limited.limited_service.limited.infrastructure.persistence.JpaLimitedRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Limited> findAll(Pageable pageable) {
        return jpaLimitedRepository.findAll(pageable);
    }
}

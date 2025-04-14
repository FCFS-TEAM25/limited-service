package com.sparta.limited.limited_service.limited.infrastructure.persistence;

import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLimitedRepository extends JpaRepository<Limited, UUID> {

    List<Limited> findByStatusAndStartDateBefore(LimitedStatus status, LocalDateTime now);

    List<Limited> findByStatusAndEndDateBefore(LimitedStatus status, LocalDateTime now);
}

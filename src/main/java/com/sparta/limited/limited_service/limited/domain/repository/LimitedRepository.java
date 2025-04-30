package com.sparta.limited.limited_service.limited.domain.repository;

import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LimitedRepository {

    Limited save(Limited limited);

    Limited findById(UUID limitedEventId);

    List<Limited> findAll();

    List<Limited> findByStatusAndStartDateBefore(LimitedStatus limitedStatus, LocalDateTime now);

    List<Limited> findByStatusAndEndDateBefore(LimitedStatus limitedStatus, LocalDateTime now);
}

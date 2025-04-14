package com.sparta.limited.limited_service.limited.domain.repository;

import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.Limited.LimitedStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LimitedRepository {

    void save(Limited limited);

    Limited findById(UUID limitedEventId);

    Page<Limited> findAll(Pageable pageable);

    List<Limited> findByStatusAndStartDateBefore(LimitedStatus limitedStatus, LocalDateTime now);

    List<Limited> findByStatusAndEndDateBefore(LimitedStatus limitedStatus, LocalDateTime now);
}

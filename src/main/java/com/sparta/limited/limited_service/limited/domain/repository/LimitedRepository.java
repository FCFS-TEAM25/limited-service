package com.sparta.limited.limited_service.limited.domain.repository;

import com.sparta.limited.limited_service.limited.domain.model.Limited;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LimitedRepository {

    void save(Limited limited);

    Limited findById(UUID limitedEventId);

    Page<Limited> findAll(Pageable pageable);
}

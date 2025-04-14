package com.sparta.limited.limited_service.limited.domain.repository;

import com.sparta.limited.limited_service.limited.domain.model.Limited;
import java.util.UUID;

public interface LimitedRepository {

    void save(Limited limited);

    Limited findById(UUID limitedEventId);
}

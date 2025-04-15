package com.sparta.limited.limited_service.limited.infrastructure.persistence;

import com.sparta.limited.limited_service.limited.domain.model.LimitedPurchaseUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLimitedPurchaseRepository extends JpaRepository<LimitedPurchaseUser, UUID> {

}

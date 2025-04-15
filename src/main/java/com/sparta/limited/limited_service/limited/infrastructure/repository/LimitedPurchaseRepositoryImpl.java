package com.sparta.limited.limited_service.limited.infrastructure.repository;

import com.sparta.limited.limited_service.limited.domain.model.LimitedPurchaseUser;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedPurchaseRepository;
import com.sparta.limited.limited_service.limited.infrastructure.persistence.JpaLimitedPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LimitedPurchaseRepositoryImpl implements LimitedPurchaseRepository {

    private final JpaLimitedPurchaseRepository jpaLimitedPurchaseRepository;

    @Override
    public void save(LimitedPurchaseUser purchaseUser) {
        jpaLimitedPurchaseRepository.save(purchaseUser);
    }
}

package com.sparta.limited.limited_service.limited.domain.repository;

import com.sparta.limited.limited_service.limited.domain.model.LimitedPurchaseUser;

public interface LimitedPurchaseRepository {

    void save(LimitedPurchaseUser purchaseUser);
}

package com.sparta.limited.limited_service.limited_product.infrastructure.repository;

import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import com.sparta.limited.limited_service.limited_product.infrastructure.persistence.JpaLimitedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LimitedProductRepositoryImpl implements LimitedProductRepository {

    private final JpaLimitedProductRepository jpaLimitedProductRepository;

    @Override
    public void save(LimitedProduct limitedProduct) {
        jpaLimitedProductRepository.save(limitedProduct);
    }
}

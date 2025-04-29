package com.sparta.limited.limited_service.limited_product.infrastructure.repository;

import com.sparta.limited.limited_service.limited_product.domain.exception.LimitedProductNotFoundException;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import com.sparta.limited.limited_service.limited_product.infrastructure.persistence.JpaLimitedProductRepository;
import java.util.UUID;
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

    @Override
    public LimitedProduct findById(UUID limitedProductId) {
        return jpaLimitedProductRepository.findById(limitedProductId)
            .orElseThrow(() -> new LimitedProductNotFoundException(limitedProductId));
    }


    @Override
    public LimitedProduct findByProductId(UUID productId) {
        return jpaLimitedProductRepository.findByProductId(productId)
            .orElseThrow(() -> new LimitedProductNotFoundException(productId));
    }
}

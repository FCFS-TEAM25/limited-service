package com.sparta.limited.limited_service.limited_product.infrastructure.repository;

import com.sparta.limited.limited_service.limited_product.domain.exception.LimitedProductNotFoundException;
import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import com.sparta.limited.limited_service.limited_product.domain.repository.LimitedProductRepository;
import com.sparta.limited.limited_service.limited_product.infrastructure.persistence.JpaLimitedProductRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    public Map<UUID, LimitedProduct> findAllById(List<UUID> limitedProductIds) {
        return jpaLimitedProductRepository.findAllById(limitedProductIds)
            .stream()
            .collect(Collectors.toMap(LimitedProduct::getId, Function.identity()));
    }
}

package com.sparta.limited.limited_service.limited_product.infrastructure.persistence;

import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLimitedProductRepository extends JpaRepository<LimitedProduct, UUID> {

    Optional<LimitedProduct> findByProductId(UUID productId);
}

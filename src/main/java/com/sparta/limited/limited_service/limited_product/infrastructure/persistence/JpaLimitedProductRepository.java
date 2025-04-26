package com.sparta.limited.limited_service.limited_product.infrastructure.persistence;

import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface JpaLimitedProductRepository extends JpaRepository<LimitedProduct, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from LimitedProduct p where p.id = :id")
    Optional<LimitedProduct> findByIdWithLock(UUID id);

    @Query("select count(p) > 0 from LimitedProduct p where p.id = :id and p.quantity > 0")
    boolean existsQuantityGreaterThanZero(UUID id);

    Optional<LimitedProduct> findByProductId(UUID productId);
}

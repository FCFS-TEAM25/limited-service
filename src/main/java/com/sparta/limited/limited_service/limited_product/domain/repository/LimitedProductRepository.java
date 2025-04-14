package com.sparta.limited.limited_service.limited_product.domain.repository;

import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LimitedProductRepository {

    void save(LimitedProduct limitedProduct);

    LimitedProduct findById(UUID limitedProductId);

    Map<UUID, LimitedProduct> findAllById(List<UUID> limitedProductIds);
}

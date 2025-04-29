package com.sparta.limited.limited_service.limited_product.domain.repository;

import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;
import java.util.UUID;

public interface LimitedProductRepository {

    void save(LimitedProduct limitedProduct);

    LimitedProduct findById(UUID limitedProductId);

    LimitedProduct findByProductId(UUID productId);
    
}

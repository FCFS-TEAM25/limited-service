package com.sparta.limited.limited_service.limited_product.domain.repository;

import com.sparta.limited.limited_service.limited_product.domain.model.LimitedProduct;

public interface LimitedProductRepository {

    void save(LimitedProduct limitedProduct);
}

package com.sparta.limited.limited_service.limited_product.domain.model;

import com.sparta.limited.common_module.common.BaseEntity;
import com.sparta.limited.limited_service.limited_product.domain.exception.LimitedProductNoQuantityException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_limited_product")
@SQLRestriction("deleted_at IS NULL")
public class LimitedProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    private LimitedProduct(UUID productId, String title, String description,
        BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public static LimitedProduct of(UUID productId, String title, String description,
        BigDecimal price, Integer quantity) {
        return new LimitedProduct(productId, title, description, price, quantity);
    }

    public void updateQuantity() {
        if (quantity <= 0) {
            throw new LimitedProductNoQuantityException(productId);
        }
        this.quantity--;
    }
}

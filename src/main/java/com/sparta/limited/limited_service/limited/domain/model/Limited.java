package com.sparta.limited.limited_service.limited.domain.model;


import com.sparta.limited.common_module.common.BaseEntity;
import com.sparta.limited.limited_service.limited.domain.model.validator.LimitedDateValidator;
import com.sparta.limited.limited_service.limited.domain.model.validator.LimitedStatusValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_limited")
@SQLRestriction("deleted_at IS NULL")
public class Limited extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "limited_product_id", nullable = false)
    private UUID limitedProductId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LimitedStatus status;

    @Version
    private Integer version;

    private Limited(UUID limitedProductId, LocalDateTime startDate,
        LocalDateTime endDate, LimitedStatus status) {

        LimitedDateValidator.validateDate(startDate, endDate);

        this.limitedProductId = limitedProductId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static Limited createOf(UUID limitedProductId, LocalDateTime startDate,
        LocalDateTime endDate) {
        return new Limited(limitedProductId, startDate, endDate, LimitedStatus.PENDING);
    }

    public void updateStatusClose() {
        LimitedStatusValidator.validateStatusAlreadyClose(this.status);
        status = LimitedStatus.CLOSED;
    }

    public void updateStatusActive() {
        status = LimitedStatus.ACTIVE;
    }

    public enum LimitedStatus {
        PENDING("한정수량 이벤트 진행 대기"),
        ACTIVE("한정수량 이벤트 진행중"),
        CLOSED("한정수량 이벤트 진행 종료");

        private final String description;

        LimitedStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}

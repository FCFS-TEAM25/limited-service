package com.sparta.limited.limited_service.limited.application.service;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.request.LimitedOrderRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedListResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedOrderResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedProductResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseAcceptResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseOrderResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedPurchaseResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedReadResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedUpdateStatusResponse;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedEventDetailMapper;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedEventMapper;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedOrderMapper;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedPurchaseMapper;
import com.sparta.limited.limited_service.limited.application.service.limited_product.LimitedProductFacade;
import com.sparta.limited.limited_service.limited.application.service.limited_product.dto.LimitedProductInfo;
import com.sparta.limited.limited_service.limited.application.service.order.OrderClientService;
import com.sparta.limited.limited_service.limited.application.validator.PurchaseLuaValidate;
import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventUnableChangeStatusException;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.model.LimitedPurchaseUser;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedPurchaseRepository;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import com.sparta.limited.limited_service.limited.infrastructure.redis.RedisService;
import jakarta.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Limited Event Service")
public class LimitedService {

    private final LimitedRepository limitedRepository;
    private final LimitedPurchaseRepository limitedPurchaseRepository;
    private final LimitedProductFacade limitedProductFacade;
    private final OrderClientService orderClientService;
    private final RedisService redisService;
    private final DefaultRedisScript<Long> purchaseLuaScript;

    @Transactional
    public LimitedCreateResponse createLimitedEvent(UUID limitedProductId,
        LimitedCreateRequest request) {

        Limited limited = LimitedEventMapper.toCreateEntity(limitedProductId, request);

        LimitedProductInfo productInfo = limitedProductFacade.getLimitedProductInfo(
            limitedProductId);

        Limited saveLimited = limitedRepository.save(limited);

        redisService.generateInitQuantity(saveLimited.getId(), productInfo.quantity(),
            saveLimited.getEndDate());

        return LimitedEventMapper.toCreateResponse(saveLimited);
    }

    @Transactional(readOnly = true)
    public LimitedReadResponse getLimitedEvent(UUID limitedEventId) {

        Limited limited = limitedRepository.findById(limitedEventId);
        LimitedResponse limitedResponse = LimitedEventMapper.toResponse(limited);

        LimitedProductResponse limitedProductResponse = limitedProductFacade.getLimitedProduct(
            limited.getLimitedProductId());

        return LimitedEventDetailMapper.toReadResponse(limitedResponse, limitedProductResponse);
    }

    @Transactional(readOnly = true)
    public Page<LimitedListResponse> getLimitedEvents(Pageable pageable) {

        List<LimitedListResponse> result = new ArrayList<>();

        List<Limited> limitedList = limitedRepository.findAll();

        limitedList.forEach(limited -> {
            LimitedProductResponse limitedProductResponse = limitedProductFacade.getLimitedProduct(
                limited.getLimitedProductId());
            result.add(
                LimitedEventMapper.toListResponse(limited, limitedProductResponse.getTitle()));
        });

        return new PageImpl<>(result, pageable, limitedList.size());
    }

    @Transactional
    public LimitedUpdateStatusResponse updateStatusClose(UUID limitedEventId) {

        Limited limited = limitedRepository.findById(limitedEventId);

        try {
            limited.updateStatusClose();
            return LimitedEventMapper.toUpdateStatusResponse(limited);
        } catch (OptimisticLockException | StaleObjectStateException e) {
            log.warn("이벤트 종료 실패 (낙관적 락) - id : {}, 에러메세지 : {} ", limitedEventId, e.getMessage());
            throw new LimitedEventUnableChangeStatusException(limitedEventId);
        }

    }

    @Transactional
    public LimitedPurchaseOrderResponse purchaseOrderSchedule(Long userId, UUID limitedEventId,
        Integer quantity) {

        Limited limited = limitedRepository.findById(limitedEventId);

        LimitedProductInfo limitedProductInfo = limitedProductFacade.decreaseLimitedProductQuantity(
            quantity, limited.getLimitedProductId());

        UUID orderId = orderClientService.createOrder(limitedEventId, limited.getEndDate(),
            userId, limitedProductInfo, quantity);
        LimitedOrderResponse orderResponse = LimitedOrderMapper.toOrderResponse(orderId,
            limitedProductInfo, quantity);

        LimitedPurchaseUser purchaseUser = LimitedPurchaseUser.of(userId, limitedEventId);
        limitedPurchaseRepository.save(purchaseUser);

        LimitedPurchaseResponse purchaseResponse = LimitedPurchaseMapper.toPurchaseResponse(
            purchaseUser);

        return LimitedEventDetailMapper.toPurchaseOrderResponse(purchaseResponse, orderResponse);
    }


    public LimitedPurchaseAcceptResponse purchaseOrderRedis(Long userId, UUID limitedEventId,
        LimitedOrderRequest request) {

        List<String> keys = this.generateKeys(userId, limitedEventId);
        Object[] args = this.generateArgs(request.getQuantity(), userId, limitedEventId);

        Long result = redisService.executeLuaScript(purchaseLuaScript, keys, args);

        PurchaseLuaValidate.validate(result, limitedEventId);

        return LimitedPurchaseAcceptResponse.of("구매 요청이 정상적으로 접수되었습니다.");
    }


    private List<String> generateKeys(Long userId, UUID limitedEventId) {
        return List.of(
            "limited_event_status:" + limitedEventId,
            "limited_purchase:" + limitedEventId + ":" + userId,
            "limited_quantity:" + limitedEventId
        );
    }

    private Object[] generateArgs(Integer orderQuantity, Long userId, UUID limitedEventId) {
        return new Object[]{
            "1",
            "PENDING",
            orderQuantity.toString(),
            limitedEventId.toString(),
            userId.toString(),
        };
    }

}

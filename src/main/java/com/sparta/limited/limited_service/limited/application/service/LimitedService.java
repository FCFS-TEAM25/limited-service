package com.sparta.limited.limited_service.limited.application.service;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedListResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedProductResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedReadResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedResponse;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedUpdateCloseStatusResponse;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedMapper;
import com.sparta.limited.limited_service.limited.application.service.limited_product.LimitedProductFacade;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LimitedService {

    private final LimitedRepository limitedRepository;
    private final LimitedProductFacade limitedProductFacade;

    @Transactional
    public LimitedCreateResponse createLimitedEvent(UUID limitedProductId,
        LimitedCreateRequest request) {

        Limited limited = LimitedMapper.toCreateEntity(limitedProductId, request);

        limitedRepository.save(limited);
        return LimitedMapper.toCreateResponse(limited);
    }

    @Transactional(readOnly = true)
    public LimitedReadResponse getLimitedEvent(UUID limitedEventId) {

        Limited limited = limitedRepository.findById(limitedEventId);
        LimitedResponse limitedResponse = LimitedMapper.toResponse(limited);

        LimitedProductResponse limitedProductResponse = limitedProductFacade.getLimitedProduct(
            limited.getLimitedProductId());

        return LimitedMapper.toReadResponse(limitedResponse, limitedProductResponse);
    }

    @Transactional(readOnly = true)
    public Page<LimitedListResponse> getLimitedEvents(Pageable pageable) {

        Page<Limited> limitedPage = limitedRepository.findAll(pageable);

        Map<UUID, String> limitedProductTitles = limitedProductFacade.getLimitedProductTitles(
            limitedPage.stream().map(Limited::getLimitedProductId).distinct().toList());

        List<LimitedListResponse> responses = limitedPage.stream()
            .map(limited -> LimitedMapper.toListResponse(
                limited, limitedProductTitles.get(limited.getLimitedProductId())))
            .toList();

        return new PageImpl<>(responses, pageable, limitedPage.getTotalElements());
    }

    @Transactional
    public LimitedUpdateCloseStatusResponse updateStatusClose(UUID limitedEventId) {

        Limited limited = limitedRepository.findById(limitedEventId);

        limited.updateStatusClose();

        return LimitedMapper.toUpdateStatusResponse(limited);
    }
}

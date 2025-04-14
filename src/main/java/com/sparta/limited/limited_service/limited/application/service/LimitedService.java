package com.sparta.limited.limited_service.limited.application.service;

import com.sparta.limited.limited_service.limited.application.dto.request.LimitedCreateRequest;
import com.sparta.limited.limited_service.limited.application.dto.response.LimitedCreateResponse;
import com.sparta.limited.limited_service.limited.application.mapper.LimitedMapper;
import com.sparta.limited.limited_service.limited.domain.model.Limited;
import com.sparta.limited.limited_service.limited.domain.repository.LimitedRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitedService {

    private final LimitedRepository limitedRepository;

    public LimitedCreateResponse createLimitedEvent(UUID limitedProductId,
        LimitedCreateRequest request) {

        Limited limited = LimitedMapper.toCreateEntity(limitedProductId, request);

        limitedRepository.save(limited);
        return LimitedMapper.toCreateResponse(limited);
    }
}

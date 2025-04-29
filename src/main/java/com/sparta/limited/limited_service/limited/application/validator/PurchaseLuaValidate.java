package com.sparta.limited.limited_service.limited.application.validator;

import com.sparta.limited.limited_service.limited.domain.exception.DuplicateUserIdException;
import com.sparta.limited.limited_service.limited.domain.exception.LimitedEventNotActiveException;
import com.sparta.limited.limited_service.limited_product.domain.exception.LimitedProductNoQuantityException;
import io.lettuce.core.RedisException;
import java.util.UUID;

public class PurchaseLuaValidate {

    public static void validate(Long result, UUID limitedEventId) {
        if (result == null) {
            throw new RedisException("Result is null");
        }

        if (result == 0L) {
            return;
        } else if (result == 1L) {
            throw new LimitedEventNotActiveException(limitedEventId);
        } else if (result == 2L) {
            throw new DuplicateUserIdException(limitedEventId);
        } else if (result == 3L) {
            throw new LimitedProductNoQuantityException(limitedEventId);
        } else {
            throw new RedisException("Invalid result value: " + result);
        }
    }

}

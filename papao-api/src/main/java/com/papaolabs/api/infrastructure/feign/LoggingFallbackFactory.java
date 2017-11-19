package com.papaolabs.api.infrastructure.feign;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;

public interface LoggingFallbackFactory<T> extends FallbackFactory<T> {
    @Override
    default T create(Throwable cause) {
        if (cause instanceof HystrixRuntimeException) {
            HystrixRuntimeException hystrixRuntimeException = HystrixRuntimeException.class.cast(cause);
            if (HystrixRuntimeException.FailureType.SHORTCIRCUIT == hystrixRuntimeException.getFailureType()) {
                logger().warn("FALLBACK SHORTCIRCUIT");
                return fallback();
            }
        }
        logger().error("FALLBACK {}", cause.toString());
        return fallback();
    }

    T fallback();

    Logger logger();
}

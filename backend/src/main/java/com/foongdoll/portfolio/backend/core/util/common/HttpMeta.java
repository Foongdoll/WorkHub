package com.foongdoll.portfolio.backend.core.util.common;

import org.slf4j.MDC;

import java.time.Instant;

public record HttpMeta(String traceId, Instant timestamp) {
    public static HttpMeta now() {
        return new HttpMeta(
                MDC.get("traceId"), // 필터에서 세팅
                Instant.now()
        );
    }
}

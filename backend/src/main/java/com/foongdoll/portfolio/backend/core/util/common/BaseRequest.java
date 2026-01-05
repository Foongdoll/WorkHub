package com.foongdoll.portfolio.backend.core.util.common;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "기본 요청")
public record BaseRequest<T>(T data) {
}

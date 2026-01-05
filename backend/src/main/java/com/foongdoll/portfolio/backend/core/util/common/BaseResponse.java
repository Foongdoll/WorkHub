package com.foongdoll.portfolio.backend.core.util.common;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "기본 응답 포맷")
public record BaseResponse<T>(
        @Schema(description = "성공 여부", example = "true")
        boolean success,

        @Schema(description = "응답 코드", example = "OK")
        String code,

        @Schema(description = "메시지", example = "로그인 성공")
        String message,

        @Schema(description = "메타 정보")
        HttpMeta httpMeta,

        @Schema(description = "응답 데이터")
        T data
) {

    /* ===== 성공 ===== */
    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>(
                true,
                "OK",
                "요청이 성공했습니다.",
                HttpMeta.now(),
                null
        );
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(
                true,
                "OK",
                "요청이 성공했습니다.",
                HttpMeta.now(),
                data
        );
    }

    public static <T> BaseResponse<T> ok(T data, String message) {
        return new BaseResponse<>(
                true,
                "OK",
                message,
                HttpMeta.now(),
                data
        );
    }

    /* ===== 실패 ===== */
    public static BaseResponse<Void> fail(String code, String message) {
        return new BaseResponse<>(
                false,
                code,
                message,
                HttpMeta.now(),
                null
        );
    }
}

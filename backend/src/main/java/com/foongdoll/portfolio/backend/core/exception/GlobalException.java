package com.foongdoll.portfolio.backend.core.exception;

import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;
import com.foongdoll.portfolio.backend.core.logging.CustomLogger;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BaseException.class)
    public BaseResponse<Void> handleBaseException(BaseException ex) {
        ErrorCode ec = ex.getErrorCode();

        // 서버 에러급은 error, 그 외는 warn 정도로 구분
        if (ec.status().is5xxServerError()) {
            CustomLogger.error("BaseException(5xx): " + ec.code() + " - " + ex.getMessage(), ex);
        } else {
            CustomLogger.warn("BaseException: " + ec.code() + " - " + ex.getMessage());
        }

        return BaseResponse.fail(ec.code(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Void> handleValidationException(MethodArgumentNotValidException ex) {
        CustomLogger.warn("Validation error occurred");
        return BaseResponse.fail(ErrorCode.VALIDATION_ERROR.code(), ErrorCode.VALIDATION_ERROR.defaultMessage());
    }


    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception ex) {
        CustomLogger.error("Unhandled exception occurred", ex);
        return BaseResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR.code(), ErrorCode.INTERNAL_SERVER_ERROR.defaultMessage());
    }
}

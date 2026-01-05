package com.foongdoll.portfolio.backend.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        BaseResponse<Void> body = BaseResponse.fail(
                "FORBIDDEN",
                "접근 권한이 없습니다."
        );

        response.getWriter().write(
                objectMapper.writeValueAsString(body)
        );
    }
}

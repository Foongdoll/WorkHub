package com.foongdoll.portfolio.backend.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        BaseResponse<Void> body = BaseResponse.fail(
                "UNAUTHORIZED",
                "인증이 필요합니다."
        );

        response.getWriter().write(
                objectMapper.writeValueAsString(body)
        );
    }
}

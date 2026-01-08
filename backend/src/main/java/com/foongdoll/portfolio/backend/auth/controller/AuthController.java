package com.foongdoll.portfolio.backend.auth.controller;

import com.foongdoll.portfolio.backend.auth.dto.*;
import com.foongdoll.portfolio.backend.auth.service.AuthService;
import com.foongdoll.portfolio.backend.core.annotation.ApiLogging;
import com.foongdoll.portfolio.backend.core.util.common.BaseRequest;
import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController<T> {

    private final AuthService authService;

    @ApiLogging(message = "로그인 메서드")
    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인하고 JWT를 발급합니다."
    )
    @PostMapping("authenticate")
    public BaseResponse<T> authenticate(@RequestBody BaseRequest<T> req) {return BaseResponse.ok(authService.authenticate(req));}

    @ApiLogging(message = "회원가입 메서드")
    @Operation(
            summary = "회원가입",
            description = "회원 정보를 전달받아 저장하고 인증 이메일을 전달합니다."
    )
    @PostMapping("signup")
    public BaseResponse<T> signup(@RequestBody BaseRequest<SignupDto> req) {
        return (BaseResponse<T>) BaseResponse.ok(authService.signup(req));
    }

    @ApiLogging(message = "회원 이메일 인증 메서드")
    @Operation(
            summary = "회원 이메일 인증",
            description = "회원가입 시 전달한 인증코드를 메일 내 버튼 클릭을 통해 받아 회원 상태를 승인으로 수정합니다."
    )
    @GetMapping("verify")
    public BaseResponse<T> memberEmailAuthenticate(@RequestParam("token") String token) {
        return BaseResponse.ok(authService.verify(token));
    }

}

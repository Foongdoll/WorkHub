package com.foongdoll.portfolio.backend.auth.service.impl;

import com.foongdoll.portfolio.backend.auth.dto.SignupDto;
import com.foongdoll.portfolio.backend.auth.entity.Role;
import com.foongdoll.portfolio.backend.auth.entity.User;
import com.foongdoll.portfolio.backend.auth.entity.UserRole;
import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.auth.repository.RoleRepository;
import com.foongdoll.portfolio.backend.auth.repository.UserRepository;
import com.foongdoll.portfolio.backend.auth.repository.UserRoleRepository;
import com.foongdoll.portfolio.backend.auth.repository.UserVerificationTokenRepository;
import com.foongdoll.portfolio.backend.auth.service.AuthService;
import com.foongdoll.portfolio.backend.auth.service.EmailVerifyService;
import com.foongdoll.portfolio.backend.core.annotation.ApiLogging;
import com.foongdoll.portfolio.backend.core.exception.BaseException;
import com.foongdoll.portfolio.backend.core.exception.ErrorCode;
import com.foongdoll.portfolio.backend.core.logging.CustomLogger;
import com.foongdoll.portfolio.backend.core.util.common.BaseRequest;
import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;
import com.foongdoll.portfolio.backend.core.util.mail.MailUtil;
import com.foongdoll.portfolio.backend.core.util.mail.template.VerifyMailTemplate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final UserVerificationTokenRepository userVerificationTokenRepository;
    private final EmailVerifyService emailVerifyService;
    private final AuthenticationManager authenticationManager;
    private final MailUtil mailSender;
    private final PasswordEncoder passwordEncoder;

    @ApiLogging(message = "로그인 - DB 조회 -> 인증 및 인가 -> 완료", params = true)
    @Override
    public <T> T authenticate(BaseRequest<T> req) {

        
        return null;
    }

    @ApiLogging(
            message = "회원가입 - 회원 정보 저장 -> 인증 토큰 생성 -> 이메일 발송",
            params = true
    )
    @Transactional
    @Override
    public BaseResponse<Void> signup(BaseRequest<SignupDto> req) {
        User user = User.builder()
                .email(req.data().email())
                .password(passwordEncoder.encode(req.data().password()))
                .name(req.data().name())
                .birthDay(req.data().birthDay())
                .phone(req.data().phone())
                .gender(req.data().gender())
                .status(ApprovalState.PENDING)
                .build();

        userRepository.save(user);
        CustomLogger.section("회원 정보 저장 완료");

        // 인증 토큰 발급
        String token = emailVerifyService.issueEmailVerifyToken(user);

        String verifyUrl =
                "http://localhost:8080/api/v1/auth/verify?token=" + token;

        mailSender.sendHtmlMail(
                user.getEmail(),
                "[WORKHUB] 회원가입 이메일 인증",
                VerifyMailTemplate.signup(user.getName(), verifyUrl)
        );

        return BaseResponse.ok();
    }

    @Override
    @Transactional
    public <T> T verify(String token) {

        // 1. 토큰 검증 + User 승인 처리
        User user = emailVerifyService.verifyEmail(token);
        CustomLogger.section("이메일 인증 완료 - 사용자 승인 처리");

        // 2. ROLE_USER 조회
        Role role = roleRepository.findByCode("ROLE_USER")
                .orElseThrow(() ->
                        new BaseException(ErrorCode.ROLE_NOT_FOUND, "기본 권한이 존재하지 않습니다.")
                );



        // 3. 이미 권한이 있으면 중복 부여 방지
        boolean exists = userRoleRepository
                .existsByUserIdAndRoleId(user.getId(), role.getId());

        if (!exists) {
            userRoleRepository.save(
                    UserRole.builder()
                            .user(user)
                            .role(role)
                            .build()
            );
            CustomLogger.section("ROLE_USER 권한 부여 완료");
        }

        // 4. 응답
        return (T) BaseResponse.ok("이메일 인증이 완료되었습니다.");
    }



}

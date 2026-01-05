package com.foongdoll.portfolio.backend.auth.service;

import com.foongdoll.portfolio.backend.auth.entity.*;
import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.auth.enums.TokenPurpose;
import com.foongdoll.portfolio.backend.auth.enums.TokenStatus;
import com.foongdoll.portfolio.backend.auth.repository.UserVerificationTokenRepository;
import com.foongdoll.portfolio.backend.core.exception.BaseException;
import com.foongdoll.portfolio.backend.core.exception.ErrorCode;
import com.foongdoll.portfolio.backend.core.util.common.TokenHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final UserVerificationTokenRepository tokenRepository;

    private static final Duration VERIFY_TTL = Duration.ofHours(24);

    /** 회원가입 시 호출: UUID 원문 반환(메일 링크에 넣을 값) */
    @Transactional
    public String issueEmailVerifyToken(User user) {
        String rawToken = UUID.randomUUID().toString(); // 사용자가 받는 값(원문)
        String tokenHash = TokenHashUtil.sha256Hex(rawToken);

        UserVerificationToken token = UserVerificationToken.builder()
                .user(user)
                .tokenHash(tokenHash)
                .purpose(TokenPurpose.EMAIL_VERIFY)
                .status(TokenStatus.ACTIVE)
                .expiresAt(Instant.now().plus(VERIFY_TTL))
                .build();

        tokenRepository.save(token);
        return rawToken;
    }

    /** 링크 클릭 시 호출: 토큰 검증 → user 승인(APPROVED) → 토큰 USED 처리 */
    @Transactional
    public User verifyEmail(String rawToken) {
        String tokenHash = TokenHashUtil.sha256Hex(rawToken);

        UserVerificationToken token = tokenRepository
                .findFirstByTokenHashAndPurposeAndStatus(tokenHash, TokenPurpose.EMAIL_VERIFY, TokenStatus.ACTIVE)
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_TOKEN, "유효하지 않은 인증 토큰입니다."));

        Instant now = Instant.now();
        if (token.isExpired(now)) {
            token.markExpired();
            throw new BaseException(ErrorCode.TOKEN_EXPIRED, "인증 토큰이 만료되었습니다.");
        }

        User user = token.getUser();

        if (user.getStatus() == ApprovalState.APPROVED) {
            token.markUsed(); // 이미 승인이라도 재사용은 막음
            throw new BaseException(ErrorCode.ALREADY_APPROVED, "이미 승인된 회원입니다.");
        }

        user.approve();
        token.markUsed();
        return user;
    }
}

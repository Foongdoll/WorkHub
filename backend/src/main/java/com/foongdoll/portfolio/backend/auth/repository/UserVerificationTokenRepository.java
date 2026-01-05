package com.foongdoll.portfolio.backend.auth.repository;

import com.foongdoll.portfolio.backend.auth.entity.UserVerificationToken;
import com.foongdoll.portfolio.backend.auth.enums.TokenPurpose;
import com.foongdoll.portfolio.backend.auth.enums.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {

    Optional<UserVerificationToken> findFirstByTokenHashAndPurposeAndStatus(
            String tokenHash,
            TokenPurpose purpose,
            TokenStatus status
    );
}

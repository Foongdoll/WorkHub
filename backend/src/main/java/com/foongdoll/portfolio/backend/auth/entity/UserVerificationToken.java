package com.foongdoll.portfolio.backend.auth.entity;

import com.foongdoll.portfolio.backend.auth.enums.TokenPurpose;
import com.foongdoll.portfolio.backend.auth.enums.TokenStatus;
import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Table(
        name = "user_verification_tokens",
        indexes = {
                @Index(name = "ix_uvt_token_hash", columnList = "tokenHash"),
                @Index(name = "ix_uvt_user_purpose", columnList = "user_id,purpose")
        }
)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVerificationToken extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 64)
    private String tokenHash; // SHA-256 hex

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TokenPurpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TokenStatus status;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column
    private Instant usedAt;

    public boolean isExpired(Instant now) {
        return expiresAt.isBefore(now);
    }

    public boolean isActive() {
        return status == TokenStatus.ACTIVE;
    }

    public void markUsed() {
        this.status = TokenStatus.USED;
        this.usedAt = Instant.now();
    }

    public void markExpired() {
        this.status = TokenStatus.EXPIRED;
    }
}

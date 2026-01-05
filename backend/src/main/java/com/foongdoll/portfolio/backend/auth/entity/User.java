package com.foongdoll.portfolio.backend.auth.entity;

import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.auth.enums.Gender;
import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Table(
        name = "users",
        indexes = {
                @Index(name = "ix_users_email", columnList = "email")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_users_email", columnNames = {"email"})
        }
)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApprovalState status;

    @Column
    private Instant emailVerifiedAt;

    public void approve() {
        this.status = ApprovalState.APPROVED;
        this.emailVerifiedAt = Instant.now();
    }

    public void markPending() {
        this.status = ApprovalState.PENDING;
    }

}

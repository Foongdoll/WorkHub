package com.foongdoll.portfolio.backend.auth.entity;

import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Table(
        name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "ux_user_role",
                        columnNames = {"user_id", "role_id"}
                )
        }
)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private Instant assignedAt;
}

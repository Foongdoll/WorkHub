package com.foongdoll.portfolio.backend.auth.entity;

import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(
        name = "roles",
        indexes = {
                @Index(name = "ix_roles_code", columnList = "code")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_roles_code", columnNames = {"code"})
        }
)
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    /**
     * 권한 코드 (Spring Security 권한과 1:1 매핑)
     * 예: ROLE_USER, ROLE_ADMIN
     */
    @Column(nullable = false, length = 50, unique = true)
    private String code;

    /**
     * 권한 설명 (UI/관리자용)
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * 사용 여부 (권한 비활성화용)
     */
    @Column(nullable = false)
    private boolean active;
}

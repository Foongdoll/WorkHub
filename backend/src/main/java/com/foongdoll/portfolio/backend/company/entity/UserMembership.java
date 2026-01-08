package com.foongdoll.portfolio.backend.company.entity;

import com.foongdoll.portfolio.backend.auth.entity.User;
import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_memberships",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_membership_user_company", columnNames = {"user_cd", "company_cd"})
        },
        indexes = {
                @Index(name = "ix_membership_company_state", columnList = "company_cd, approval_state"),
                @Index(name = "ix_membership_user", columnList = "user_cd"),
                @Index(name = "ix_membership_dept", columnList = "department_cd")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership extends BaseEntity {

    // 유저(사람)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_cd", nullable = false)
    private User user;

    // 회사(조직)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_cd", nullable = false)
    private Company company;

    // 부서(선택: 승인 후 배정될 수도 있으니 nullable 권장)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_cd")
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name="approval_state", length = 20, nullable = false)
    private ApprovalState approvalState;

    // 회사 내부 직책/직급 (옵션)
    @Column(name="position", length = 50)
    private String position;

    // 사번(옵션: 회사마다 다름 → users에 넣으면 안 됨)
    @Column(name="employee_no", length = 50)
    private String employeeNo;

    // 승인 완료 시각(옵션)
    @Column(name="joined_at")
    private LocalDateTime joinedAt;

    // 퇴사/비활성 처리용
    @Column(name="active", nullable = false)
    private boolean active;
}
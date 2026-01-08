package com.foongdoll.portfolio.backend.company.entity;

import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "companies",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_company_biz_no", columnNames = {"biz_no"}),
                @UniqueConstraint(name = "ux_company_domain", columnNames = {"domain"})
        },
        indexes = {
                @Index(name = "ix_company_name", columnList = "name"),
                @Index(name = "ix_company_status", columnList = "status")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Company extends BaseEntity {

    @Column(name="name", length = 150, nullable = false)
    private String name;

    // 사업자번호(권장: 중복 방지 키)
    @Column(name="biz_no", length = 20, nullable = false, unique = true)
    private String bizNo;

    // 회사 이메일 도메인 (ex: foongdoll.com)
    @Column(name="domain", length = 100, nullable = false)
    private String domain;

    @Column(name="contact_email", length = 150)
    private String contactEmail;

    @Column(name="contact_phone", length = 30)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name="status", length = 20, nullable = false)
    private ApprovalState status;
}
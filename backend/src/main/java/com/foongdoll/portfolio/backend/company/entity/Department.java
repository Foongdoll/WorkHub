package com.foongdoll.portfolio.backend.company.entity;


import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "departments",
        uniqueConstraints = {
                @UniqueConstraint(name = "ux_dept_company_name", columnNames = {"company_id", "name"})
        },
        indexes = {
                @Index(name = "ix_dept_company", columnList = "company_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Department extends BaseEntity {

    // 어떤 회사의 부서인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name="name", length = 100, nullable = false)
    private String name;

    @Column(name="description", length = 200)
    private String description;

    // 부서 트리 필요하면 사용 (없으면 제거)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Department parent;
}
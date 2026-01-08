package com.foongdoll.portfolio.backend.company.entity;

import com.foongdoll.portfolio.backend.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "company_invite")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInvite extends BaseEntity {

    private Long companyCd;

    private String companyInviteUrl;

}

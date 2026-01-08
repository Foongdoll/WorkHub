package com.foongdoll.portfolio.backend.company.dto;

import com.foongdoll.portfolio.backend.company.enums.CompanyStatus;

public record CompanyApplySearchCondition(String name, String bizNo, CompanyStatus status) {
}

package com.foongdoll.portfolio.backend.company.dto;

import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.company.enums.CompanyStatus;

public record CompanyApplySummary(
        Long companyId,
        String name,
        String bizNo,
        String domain,
        String contactEmail,
        String contactPhone,
        ApprovalState status
) {}
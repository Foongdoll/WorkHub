package com.foongdoll.portfolio.backend.company.service;

import com.foongdoll.portfolio.backend.company.dto.CompanyApplyRequest;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplyResponse;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplySearchCondition;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplySummary;
import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanySerivce {
    CompanyApplyResponse applyCompanyUsage(CompanyApplyRequest request);

    List<CompanyApplySummary> getCompanyApplyRequests(CompanyApplySearchCondition condition, Pageable pageable);

    CompanyApplyResponse updateCompanyApplyStatus(Long applyId, ApprovalState state, String memo);
}

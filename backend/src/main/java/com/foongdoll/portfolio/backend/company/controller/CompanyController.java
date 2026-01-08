package com.foongdoll.portfolio.backend.company.controller;

import com.foongdoll.portfolio.backend.company.dto.CompanyApplyRequest;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplyResponse;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplySearchCondition;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplySummary;
import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.company.service.CompanySerivce;
import com.foongdoll.portfolio.backend.core.annotation.ApiLogging;
import com.foongdoll.portfolio.backend.core.annotation.Paged;
import com.foongdoll.portfolio.backend.core.util.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanySerivce companyService;

    @ApiLogging(message = "회사 사용 신청 메서드")
    @Operation(
            summary = "회사 사용 신청",
            description = "로그인한 사용자가 특정 회사에 소속(사용) 신청을 생성합니다. 기본 상태는 PENDING(대기)입니다."
    )
    @PostMapping("apply")
    public BaseResponse<CompanyApplyResponse> applyCompanyUsage(@RequestBody CompanyApplyRequest request) {
        return BaseResponse.ok(companyService.applyCompanyUsage(request));
    }

    @ApiLogging(message = "회사 사용 신청 요청 조회 메서드")
    @Operation(
            summary = "회사 사용 신청 요청 조회",
            description = "회사 담당자/관리자가 회사 사용 신청 목록을 조회합니다. 상태(PENDING/APPROVED/REJECTED) 및 검색조건으로 필터링 가능합니다."
    )
    @GetMapping("apply/requests")
    public BaseResponse<List<CompanyApplySummary>> getCompanyApplyRequests(
            CompanyApplySearchCondition condition,
            @Paged(defaultSort = "createdAt,desc") Pageable pageable) {
        return BaseResponse.ok(companyService.getCompanyApplyRequests(condition, pageable));
    }

    @ApiLogging(message = "회사 사용 신청 상태 변경 메서드")
    @Operation(
            summary = "회사 사용 신청 상태 변경",
            description = "회사 담당자/관리자가 회사 사용 신청을 승인(APPROVED) 또는 반려(REJECTED) 처리합니다."
    )
    @PatchMapping("apply/{applyId}/status")
    public BaseResponse<CompanyApplyResponse> updateCompanyApplyStatus(
            @PathVariable("applyId") Long applyId,
            @RequestParam("state") ApprovalState state,
            @RequestParam(value = "memo", required = false) String memo
    ) {
        return BaseResponse.ok(companyService.updateCompanyApplyStatus(applyId, state, memo));
    }

}

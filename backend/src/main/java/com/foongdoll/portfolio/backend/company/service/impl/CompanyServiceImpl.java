package com.foongdoll.portfolio.backend.company.service.impl;

import com.foongdoll.portfolio.backend.auth.entity.User;
import com.foongdoll.portfolio.backend.auth.repository.UserRepository;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplyRequest;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplyResponse;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplySearchCondition;
import com.foongdoll.portfolio.backend.company.dto.CompanyApplySummary;
import com.foongdoll.portfolio.backend.auth.enums.ApprovalState;
import com.foongdoll.portfolio.backend.company.entity.Company;
import com.foongdoll.portfolio.backend.company.entity.CompanyInvite;
import com.foongdoll.portfolio.backend.company.entity.UserMembership;
import com.foongdoll.portfolio.backend.company.enums.CompanyStatus;
import com.foongdoll.portfolio.backend.company.repository.CompanyInviteRepository;
import com.foongdoll.portfolio.backend.company.repository.CompanyRepository;
import com.foongdoll.portfolio.backend.company.repository.UserMemberShipRepository;
import com.foongdoll.portfolio.backend.company.service.CompanySerivce;
import com.foongdoll.portfolio.backend.company.spec.CompanySpecs;
import com.foongdoll.portfolio.backend.core.annotation.ApiLogging;
import com.foongdoll.portfolio.backend.core.exception.BaseException;
import com.foongdoll.portfolio.backend.core.exception.ErrorCode;
import com.foongdoll.portfolio.backend.core.security.dto.SecurityUser;
import com.foongdoll.portfolio.backend.core.security.util.SecurityUtil;
import com.foongdoll.portfolio.backend.core.util.mail.MailUtil;
import com.foongdoll.portfolio.backend.core.util.mail.template.VerifyMailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanySerivce {

    private final SecurityUtil securityUtil = new SecurityUtil();
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final UserMemberShipRepository userMemberShipRepository;
    private final CompanyInviteRepository companyInviteRepository;
    private final MailUtil mailUtil;

    @ApiLogging(message = "회사 정보 확인 -> 중복 체크 -> 회사 정보 DB 저장 -> 완료")
    @Override
    public CompanyApplyResponse applyCompanyUsage(CompanyApplyRequest req) {
        if(companyRepository.findByBizNo(req.bizNo()).isPresent()) {
            throw new BaseException(ErrorCode.VALIDATION_ERROR, "이미 존재하는 사업자번호입니다.");
        }

        Company company = companyRepository.save(
                Company.builder()
                        .bizNo(req.bizNo())
                        .name(req.name())
                        .contactEmail(req.contactEmail())
                        .contactPhone(req.contactPhone())
                        .domain(req.domain())
                        .status(ApprovalState.PENDING).build()
        );

        User u = userRepository.findByEmail(securityUtil.getSecurityUser().getUsername());

        userMemberShipRepository.save(
                UserMembership.builder().
                        user(u)
                        .company(company)
                        .active(true)
                        .approvalState(ApprovalState.PENDING)
                        .build()
        );
        return new CompanyApplyResponse(company);
    }

    @ApiLogging(message = "회사 승인 요청 리스트 조회 - 필터: 승인|비승인, 회사명, 사업번호")
    @Override
    public List<CompanyApplySummary> getCompanyApplyRequests(CompanyApplySearchCondition condition, Pageable pageable) {
        var spec = CompanySpecs.byCondition(condition);

        // pageable 적용된 Page<Company>
        var page = companyRepository.findAll(spec, pageable);

        // List<Summary> 변환
        return page.getContent().stream()
                .map(c -> new CompanyApplySummary(
                        c.getId(),      // 엔티티 PK
                        c.getName(),    // 회사명
                        c.getBizNo(),   // 사업자번호
                        c.getDomain(),  // 도메인
                        c.getContactEmail(), // 이메일  
                        c.getContactPhone(), // 전화번호
                        c.getStatus() // 상태
                ))
                .toList();
    }

    @ApiLogging(message = "회사 승인 요청 상태 전달 -> 상태 업데이트 -> 완료")
    @Override
    public CompanyApplyResponse updateCompanyApplyStatus(Long applyId, ApprovalState state, String memo) {
        UserMembership userMembership = userMemberShipRepository.findById(applyId).orElseThrow(() -> new BaseException(ErrorCode.VALIDATION_ERROR));
        userMembership.setApprovalState(state);
        userMemberShipRepository.save(userMembership);
        userMembership.getCompany().setStatus(state);
        Company c = companyRepository.save(userMembership.getCompany());

        if(state == ApprovalState.APPROVED) {
            String inviteUrl = "http://localhost:8080/api/company/invite?code="+ UUID.randomUUID();
            companyInviteRepository.save(
                    CompanyInvite.builder()
                            .companyCd(c.getId())
                            .companyInviteUrl(inviteUrl)
                            .build()
            );


            mailUtil.sendHtmlMail(userMembership.getUser().getEmail(),c.getName()+" 플랫폼 사용 신청 승인 안내",VerifyMailTemplate.companyInviteUrl(c.getName(), inviteUrl, "-없음-", c.getContactEmail()));}
        return new CompanyApplyResponse(c);
    }
}

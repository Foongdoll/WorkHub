package com.foongdoll.portfolio.backend.company.spec;

import com.foongdoll.portfolio.backend.company.dto.CompanyApplySearchCondition;
import com.foongdoll.portfolio.backend.company.entity.Company;
import org.springframework.data.jpa.domain.Specification;

public final class CompanySpecs {

    private CompanySpecs() {}

    public static Specification<Company> byCondition(CompanyApplySearchCondition condition) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (condition == null) return predicates;

            // 회사명 LIKE (ignore-case)
            if (condition.name() != null && !condition.name().trim().isBlank()) {
                String keyword = "%" + condition.name().trim().toLowerCase() + "%";
                predicates.getExpressions().add(
                        cb.like(cb.lower(root.get("name")), keyword)
                );
            }

            // 사업자번호 LIKE (ignore-case) 또는 equals로 바꾸고 싶으면 cb.equal로 변경
            if (condition.bizNo() != null && !condition.bizNo().trim().isBlank()) {
                String keyword = "%" + condition.bizNo().trim().toLowerCase() + "%";
                predicates.getExpressions().add(
                        cb.like(cb.lower(root.get("bizNo")), keyword)
                );
            }

            // 상태 = ?
            if (condition.status() != null) {
                predicates.getExpressions().add(
                        cb.equal(root.get("status"), condition.status())
                );
            }

            return predicates;
        };
    }
}

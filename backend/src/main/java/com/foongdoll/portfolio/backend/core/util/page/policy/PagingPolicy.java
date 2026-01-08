package com.foongdoll.portfolio.backend.core.util.page.policy;

import java.util.Set;

public record PagingPolicy(
        int defaultSize,
        int maxSize,
        String defaultSort,          // ex) "createdAt,desc"
        Set<String> allowedSorts     // ex) Set.of("createdAt","updatedAt","id","name")
) {
    public static PagingPolicy defaults() {
        return new PagingPolicy(
                20,
                100,
                "createdAt,desc",
                Set.of() // 비워두면 whitelist 검사 안 함
        );
    }

    public boolean isSortWhitelistEnabled() {
        return allowedSorts != null && !allowedSorts.isEmpty();
    }
}
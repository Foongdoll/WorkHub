package com.foongdoll.portfolio.backend.core.util.page.dto;
public record PageInfo(
        int page,              // 1-base
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrev
) {}

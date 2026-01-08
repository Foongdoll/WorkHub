package com.foongdoll.portfolio.backend.core.util.page.dto;

import java.util.List;

public record Paged<T>(
        List<T> items,
        PageInfo pageInfo
) {}
package com.foongdoll.portfolio.backend.core.util.page.util;

import com.foongdoll.portfolio.backend.core.util.page.dto.PageInfo;
import com.foongdoll.portfolio.backend.core.util.page.dto.Paged;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public final class PagedConverters {

    private PagedConverters() {}

    public static <T> Paged<T> from(Page<T> page) {
        return new Paged<>(
                page.getContent(),
                toPageInfo(page)
        );
    }

    public static <E, D> Paged<D> from(Page<E> page, Function<E, D> mapper) {
        List<D> items = page.getContent().stream().map(mapper).toList();
        return new Paged<>(
                items,
                toPageInfo(page)
        );
    }

    private static PageInfo toPageInfo(Page<?> page) {
        // client는 1-base로 받는다
        int page1 = page.getNumber() + 1;
        return new PageInfo(
                page1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}

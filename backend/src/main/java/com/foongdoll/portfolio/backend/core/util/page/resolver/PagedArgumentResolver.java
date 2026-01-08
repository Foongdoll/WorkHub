package com.foongdoll.portfolio.backend.core.util.page.resolver;


import com.foongdoll.portfolio.backend.core.annotation.Paged;
import com.foongdoll.portfolio.backend.core.util.page.policy.PagingPolicy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.*;
import org.springframework.web.method.support.*;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import java.util.*;
import java.util.stream.Collectors;

public class PagedArgumentResolver implements HandlerMethodArgumentResolver {

    private final PagingPolicy policy;

    public PagedArgumentResolver(PagingPolicy policy) {
        this.policy = policy;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Paged.class)
                && Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {

        Paged paged = parameter.getParameterAnnotation(Paged.class);

        HttpServletRequest req = ((ServletWebRequest) webRequest).getRequest();

        // 1) page (1-base)
        int page1 = parseInt(req.getParameter("page"), 1);
        int page0 = Math.max(0, page1 - 1);

        // 2) size
        int defaultSize = (paged.defaultSize() > 0) ? paged.defaultSize() : policy.defaultSize();
        int size = parseInt(req.getParameter("size"), defaultSize);
        size = Math.max(1, size);
        size = Math.min(size, policy.maxSize());

        // 3) sort
        // Spring 방식: sort=createdAt,desc&sort=id,asc 처럼 여러 개 가능
        String[] sortParams = req.getParameterValues("sort");

        String defaultSort = StringUtils.hasText(paged.defaultSort()) ? paged.defaultSort() : policy.defaultSort();
        Sort sort = parseSort(sortParams, defaultSort);

        // 4) whitelist enforcement
        boolean enforce = paged.enforceSortWhitelist() || policy.isSortWhitelistEnabled();
        if (enforce && policy.isSortWhitelistEnabled()) {
            sort = filterSortByWhitelist(sort, policy.allowedSorts());
        }

        return PageRequest.of(page0, size, sort);
    }

    private int parseInt(String raw, int fallback) {
        try {
            if (!StringUtils.hasText(raw)) return fallback;
            return Integer.parseInt(raw.trim());
        } catch (Exception e) {
            return fallback;
        }
    }

    private Sort parseSort(String[] sortParams, String defaultSort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sortParams != null && sortParams.length > 0) {
            for (String sp : sortParams) {
                // sp: "createdAt,desc" or "name,asc"
                if (!StringUtils.hasText(sp)) continue;
                orders.addAll(parseOrdersFromOneParam(sp));
            }
        }

        if (orders.isEmpty() && StringUtils.hasText(defaultSort)) {
            orders.addAll(parseOrdersFromOneParam(defaultSort));
        }

        return orders.isEmpty() ? Sort.unsorted() : Sort.by(orders);
    }

    private List<Sort.Order> parseOrdersFromOneParam(String sp) {
        // allow multiple orders in single string by ';' if user wants: "a,desc;b,asc"
        List<Sort.Order> orders = new ArrayList<>();
        String[] chunks = sp.split(";");
        for (String chunk : chunks) {
            String[] parts = chunk.split(",");
            String property = parts[0].trim();
            if (!StringUtils.hasText(property)) continue;

            Sort.Direction dir = Sort.Direction.DESC;
            if (parts.length >= 2) {
                try {
                    dir = Sort.Direction.fromString(parts[1].trim());
                } catch (Exception ignored) {}
            }
            orders.add(new Sort.Order(dir, property));
        }
        return orders;
    }

    private Sort filterSortByWhitelist(Sort sort, Set<String> allowed) {
        if (sort == null || sort.isUnsorted()) return sort;
        List<Sort.Order> filtered = new ArrayList<>();
        for (Sort.Order o : sort) {
            if (allowed.contains(o.getProperty())) filtered.add(o);
        }
        return filtered.isEmpty() ? Sort.unsorted() : Sort.by(filtered);
    }
}
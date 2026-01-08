package com.foongdoll.portfolio.backend.core.config;

import com.foongdoll.portfolio.backend.core.util.page.policy.PagingPolicy;
import com.foongdoll.portfolio.backend.core.util.page.resolver.PagedArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcWebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PagedArgumentResolver(PagingPolicy.defaults()));
    }
}

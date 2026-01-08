package com.foongdoll.portfolio.backend.core.security.util;

import com.foongdoll.portfolio.backend.core.security.dto.SecurityUser;
import lombok.Getter;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    @Getter
    private SecurityUser securityUser;

    public SecurityUtil() {
        securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}

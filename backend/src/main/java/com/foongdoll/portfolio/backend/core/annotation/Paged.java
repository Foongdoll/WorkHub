package com.foongdoll.portfolio.backend.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Paged {
    // 엔드포인트마다 사이즈 기본값만 바꾸고 싶을 때
    int defaultSize() default -1;

    // 엔드포인트마다 기본 정렬만 바꾸고 싶을 때
    String defaultSort() default ""; // ex) "id,desc"

    // 이 파라미터에서 sort whitelist를 켤지 (전역 정책과 별개로)
    boolean enforceSortWhitelist() default false;
}

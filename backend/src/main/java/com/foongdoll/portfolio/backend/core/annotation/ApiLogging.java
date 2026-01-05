package com.foongdoll.portfolio.backend.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLogging {

    /** 로그에 남길 짧은 설명 */
    String message() default "";

    /** 진입 로그 찍을지 */
    boolean enter() default true;

    /** 종료 로그(소요시간 포함) 찍을지 */
    boolean exit() default true;

    /** 파라미터 로그 찍을지(민감정보 있으면 false 권장) */
    boolean params() default false;
}

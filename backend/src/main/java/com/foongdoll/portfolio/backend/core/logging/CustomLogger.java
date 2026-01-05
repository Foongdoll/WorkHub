package com.foongdoll.portfolio.backend.core.logging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CustomLogger {

    private static final int BOX_WIDTH = 72;
    private static final String HORIZONTAL = "─";
    private static final String TOP_LEFT = "┌";
    private static final String TOP_RIGHT = "┐";
    private static final String BOTTOM_LEFT = "└";
    private static final String BOTTOM_RIGHT = "┘";
    private static final String VERTICAL = "│";

    private CustomLogger() {}

    /* =========================
       기본 로그
       ========================= */

    public static void info(String message) {
        log.info(message);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    /* =========================
       박스 로그
       ========================= */

    /** 가장 큰 박스 (서버 시작, 배치 시작/종료) */
    public static void box(String title) {
        log.info("\n{}\n{}\n{}\n",
                topBorder(),
                centeredLine(title),
                bottomBorder()
        );
    }

    /** 단계/섹션 박스 */
    public static void section(String title) {
        log.info("\n{}\n{}\n{}\n",
                topBorder(),
                leftAlignedLine("▶ " + title),
                bottomBorder()
        );
    }

    /* =========================
       내부 구현
       ========================= */

    private static String topBorder() {
        return TOP_LEFT + repeat(HORIZONTAL, BOX_WIDTH) + TOP_RIGHT;
    }

    private static String bottomBorder() {
        return BOTTOM_LEFT + repeat(HORIZONTAL, BOX_WIDTH) + BOTTOM_RIGHT;
    }

    private static String centeredLine(String message) {
        int padding = BOX_WIDTH - message.length();
        int left = padding / 2;
        int right = padding - left;

        return VERTICAL
                + repeat(" ", left)
                + message
                + repeat(" ", right)
                + VERTICAL;
    }

    private static String leftAlignedLine(String message) {
        int padding = BOX_WIDTH - message.length();
        return VERTICAL
                + message
                + repeat(" ", Math.max(padding, 0))
                + VERTICAL;
    }

    private static String repeat(String s, int count) {
        return s.repeat(Math.max(0, count));
    }
}

package com.example.leapit.common.enums;

import java.util.Arrays;

public enum CareerLevel {
    ENTRY(0, "신입"),
    YEAR_1(1, "1년"),
    YEAR_2(2, "2년"),
    YEAR_3(3, "3년"),
    YEAR_4(4, "4년"),
    YEAR_5(5, "5년"),
    YEAR_6(6, "6년"),
    YEAR_7(7, "7년"),
    YEAR_8(8, "8년"),
    YEAR_9(9, "9년"),
    YEAR_10_PLUS(10, "10년 이상");

    private final int code;     // DB에 저장될 값
    private final String label; // 사용자에게 보여질 값

    CareerLevel(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static CareerLevel fromCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경력 수준 코드입니다."));
    }
}

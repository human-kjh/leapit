package com.example.leapit.common.enums;

import java.util.Arrays;

public enum EducationLevel {
    HIGH_SCHOOL(0, "고등학교"),
    ASSOCIATE(1, "전문학사"),
    BACHELOR(2, "학사"),
    MASTER(3, "석사"),
    DOCTOR(4, "박사");
    // 무관할 경우 null

    private final int code;
    private final String label;

    EducationLevel(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() { return code; }
    public String getLabel() { return label; }

    public static EducationLevel fromCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코드입니다."));
    }
}

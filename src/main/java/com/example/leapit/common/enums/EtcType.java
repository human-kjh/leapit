package com.example.leapit.common.enums;

import java.util.Arrays;

public enum EtcType {
    LANGUAGE(0, "어학"),
    CERTIFICATE(1, "자격증"),
    ACTIVITY(2, "대외활동"),
    AWARD(3, "수상이력");

    private final int code;
    private final String label;

    EtcType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static EtcType fromCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 EtcType 코드입니다."));
    }
}


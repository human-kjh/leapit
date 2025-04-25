package com.example.leapit.resume.etc;

import lombok.Data;

import java.time.LocalDate;

public class EtcResponse {
    @Data
    public static class DetailDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        //hasEndDate = true : 종료일 있음
        private Boolean hasEndDate;
        private String title;
        private Integer etcType;
        private String institutionName;

        private String description;

    }
}

package com.example.leapit.resume.experience;

import lombok.Data;

import java.time.LocalDate;

public class ExperienceResponse {
    @Data
    public static class DetailDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isEmployed;

        private String companyName;

        private String summary;
        private String position;

        // 기술스택

        private String responsibility;
    }
}

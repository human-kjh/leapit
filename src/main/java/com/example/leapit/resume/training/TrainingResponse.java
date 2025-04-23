package com.example.leapit.resume.training;

import lombok.Data;

import java.time.LocalDate;

public class TrainingResponse {
    @Data
    public static class DetailDTO {

        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isOngoing;

        private String courseName;
        private String institutionName;

        // 기술스택

        private String description;
    }
}

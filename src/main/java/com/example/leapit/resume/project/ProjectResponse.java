package com.example.leapit.resume.project;

import lombok.Data;

import java.time.LocalDate;

public class ProjectResponse {
    @Data
    public static class DetailDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isOngoing;

        private String title;

        private String summary;

        // 기술스택

        private String description;
        private String repositoryUrl;

    }
}

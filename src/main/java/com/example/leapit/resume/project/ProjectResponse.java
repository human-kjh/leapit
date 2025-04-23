package com.example.leapit.resume.project;

import com.example.leapit.resume.project.techstack.ProjectTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponse {
    @Data
    public static class DetailDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isOngoing;

        private String title;

        private String summary;

        // 기술스택
        private List<ProjectTechStack> techStacks;

        private String description;
        private String repositoryUrl;

    }
}

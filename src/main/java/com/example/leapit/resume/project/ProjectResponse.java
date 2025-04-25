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
        private List<ProjectTechStack> techStackList;

        private String description;
        private String repositoryUrl;

        public DetailDTO(Project project, List<ProjectTechStack> techStacks) {
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.isOngoing = project.getIsOngoing();
            this.title = project.getTitle();
            this.summary = project.getSummary();
            this.techStackList = techStacks;
            this.description = project.getDescription();
            this.repositoryUrl = project.getRepositoryUrl();
        }
    }
}

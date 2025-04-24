package com.example.leapit.resume.training;

import com.example.leapit.resume.training.techstack.TrainingTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class TrainingResponse {
    @Data
    public static class DetailDTO {

        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isOngoing;

        private String courseName;
        private String institutionName;

        // 기술스택
        private List<TrainingTechStack> techStackList;

        private String description;

        public DetailDTO(Training training, List<TrainingTechStack> techStacks) {
            this.startDate = training.getStartDate();
            this.endDate = training.getEndDate();
            this.isOngoing = training.getIsOngoing();
            this.courseName = training.getCourseName();
            this.institutionName = training.getInstitutionName();
            this.techStackList = techStacks;
            this.description = training.getDescription();
        }
    }
}

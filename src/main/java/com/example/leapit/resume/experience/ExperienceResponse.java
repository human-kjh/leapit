package com.example.leapit.resume.experience;

import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
        private List<ExperienceTechStack> techStackList;

        private String responsibility;

        public DetailDTO(Experience experience,List<ExperienceTechStack> techStacks) {
            this.startDate = experience.getStartDate();
            this.endDate = experience.getEndDate();
            this.isEmployed = experience.getIsEmployed();
            this.companyName = experience.getCompanyName();
            this.summary = experience.getSummary();
            this.position = experience.getPosition();
            this.techStackList = techStacks;
            this.responsibility = experience.getResponsibility();
        }
    }
}

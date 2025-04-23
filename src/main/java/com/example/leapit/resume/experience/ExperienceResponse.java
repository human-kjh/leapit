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
        private List<ExperienceTechStack> techStacks;

        private String responsibility;

    }
}

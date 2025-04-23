package com.example.leapit.application;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

public class ApplicationResponse {

    @Data
    public static class ApplicationDto {
        private String companyName;
        private String jobTitle;
        private LocalDate appliedDate;
        private Integer resumeId;
        private Integer jobPostingId;

        public ApplicationDto(String companyName, String jobTitle, LocalDate appliedDate,
                              Integer resumeId, Integer jobPostingId) {
            this.companyName = companyName;
            this.jobTitle = jobTitle;
            this.appliedDate = appliedDate;
            this.resumeId = resumeId;
            this.jobPostingId = jobPostingId;
        }
    }

    @Data
    public static class ApplicationSummaryDto {
        private Long total;
        private Long passed;
        private Long failed;

        public ApplicationSummaryDto(Long total, Long passed, Long failed) {
            this.total = total;
            this.passed = passed;
            this.failed = failed;
        }
    }
}


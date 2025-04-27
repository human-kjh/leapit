package com.example.leapit.resume.education;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class EducationResponse {
    @Data
    public static class DetailDTO {
        private LocalDate graduationDate;

        private Boolean isDropout;

        private String educationLevel;

        private String schoolName;
        private String major;

        private BigDecimal gpa;
        private BigDecimal gpaScale;
    }
}

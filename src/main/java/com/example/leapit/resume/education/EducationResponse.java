package com.example.leapit.resume.education;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class EducationResponse {
    @Data
    public static class DetailDTO {
        private LocalDate graduationDate;
        private String formattedGraduationDate;
        private Boolean isDropout;
        private String educationLevel;
        private String schoolName;
        private String major;
        private String gpa;
        private String  gpaScale;

        public DetailDTO(Education education) {
            this.graduationDate = education.getGraduationDate();
            this.formattedGraduationDate = (graduationDate != null) ? graduationDate.toString().substring(0, 7) : null;
            this.isDropout = education.getIsDropout();
            this.educationLevel = education.getEducationLevel();
            this.schoolName = education.getSchoolName();
            this.major = education.getMajor();
            this.gpa = education.getGpa() != null ? education.getGpa().toString() : null;
            this.gpaScale = education.getGpaScale() != null ? education.getGpaScale().toString() : null;
        }
    }
}

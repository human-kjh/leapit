package com.example.leapit.application.bookmark;

import lombok.Data;

import java.time.LocalDate;

public class ApplicationBookmarkResponse {

    @Data
    public static class JobPostingBookmarkDTO {
        private Integer jobPostingId;
        private String companyName;
        private String jobPostingTitle;
        private LocalDate DeadLine;

        public JobPostingBookmarkDTO(Integer jobPostingId, String companyName, String jobPostingTitle, LocalDate deadLine) {
            this.jobPostingId = jobPostingId;
            this.companyName = companyName;
            this.jobPostingTitle = jobPostingTitle;
            DeadLine = deadLine;
        }
    }

    @Data
    public static class SaveDTO {
        private Integer bookmarkId;

        public SaveDTO(Integer bookmarkId) {
            this.bookmarkId = bookmarkId;
        }
    }
}

package com.example.leapit.jobposting.bookmark;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class JobPostingBookmarkRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "jobPosting의 id가 전달되어야 합니다")
        private Integer jobPostingId;

        public JobPostingBookmark toEntity(Integer personalUserId) {
            return JobPostingBookmark.builder()
                    .jobPosting(JobPosting.builder().id(jobPostingId).build())
                    .user(User.idBuilder().id(personalUserId).build())
                    .build();
        }
    }
}

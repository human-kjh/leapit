package com.example.leapit.jobposting.bookmark;

import com.example.leapit.application.Application;
import com.example.leapit.application.bookmark.ApplicationBookmark;
import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.user.User;
import lombok.Data;

public class JobPostingBookmarkRequest {

    @Data
    public static class SaveDTO {
        private Integer jobPostingId;

        public JobPostingBookmark toEntity(Integer personalUserId) {
            return JobPostingBookmark.builder()
                    .jobPosting(JobPosting.builder().id(jobPostingId).build())
                    .user(User.idBuilder().id(personalUserId).build())
                    .build();
        }
    }
}

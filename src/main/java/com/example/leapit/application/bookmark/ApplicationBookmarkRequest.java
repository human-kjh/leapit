package com.example.leapit.application.bookmark;

import lombok.Data;
import com.example.leapit.application.Application;
import com.example.leapit.user.User;

public class ApplicationBookmarkRequest {

    @Data
    public static class SaveDTO {
        private Integer applicationId;

        public ApplicationBookmark toEntity(Integer companyUserId) {
            return ApplicationBookmark.builder()
                    .application(Application.builder().id(applicationId).build())
                    .user(User.idBuilder().id(companyUserId).build())
                    .build();
        }
    }
}
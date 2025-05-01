package com.example.leapit.application.bookmark;

import com.example.leapit.application.Application;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ApplicationBookmarkRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "application의 id가 전달되어야 합니다")
        private Integer applicationId;

        public ApplicationBookmark toEntity(Integer companyUserId) {
            return ApplicationBookmark.builder()
                    .application(Application.builder().id(applicationId).build())
                    .user(User.idBuilder().id(companyUserId).build())
                    .build();
        }
    }
}
package com.example.leapit.application;

import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class ApplicantListReqDTO {
        private Integer jobPostingId;
        private String jobPosition;
        private Boolean isClosed;
        private Boolean isViewed;
        private Boolean isBookmark;


    }
}

package com.example.leapit.application;

import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class ApplicantListReqDTO {
        private Integer jobPostingId;  // 포지션 ID
        private String jobPosition;         // 진행중, 마감됨, 전체 이렇게 3개 있음 동적 쿼리 할 예정임
        private Boolean isClosed;      // 마감 여부 (null이면 전체)

        public void convertStatusToClosed() {
            if ("inProgress".equals(isClosed)) {
                this.isClosed = false;
            } else if ("ended".equals(isClosed)) {
                this.isClosed = true;
            } else {
                this.isClosed = null;
            }
        }
    }
}

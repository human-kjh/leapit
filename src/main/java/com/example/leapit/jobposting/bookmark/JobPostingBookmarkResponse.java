package com.example.leapit.jobposting.bookmark;

import lombok.Data;

public class JobPostingBookmarkResponse {

    @Data
    public static class SaveDTO {
        private Integer bookmarkId;

        public SaveDTO(Integer bookmarkId) {
            this.bookmarkId = bookmarkId;
        }
    }
}

package com.example.leapit.application.bookmark;

import lombok.Data;

public class ApplicationBookmarkResponse {
    @Data
    public static class SaveDTO {
        private Integer bookmarkId;

        public SaveDTO(Integer bookmarkId) {
            this.bookmarkId = bookmarkId;
        }
    }
}

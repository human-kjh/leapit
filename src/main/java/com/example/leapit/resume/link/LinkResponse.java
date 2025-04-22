package com.example.leapit.resume.link;

import lombok.Data;

public class LinkResponse {
    @Data
    public static class DetailDTO {
        private String title;
        private String link;
    }
}

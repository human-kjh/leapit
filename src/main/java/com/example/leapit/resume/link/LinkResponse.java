package com.example.leapit.resume.link;

import lombok.Data;

public class LinkResponse {

    @Data
    public static class DetailDTO {
        private String title;
        private String url;

        public DetailDTO(Link link) {
            this.title = link.getTitle();
            this.url = link.getUrl();
        }
    }
}

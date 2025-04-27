package com.example.leapit.board;

import lombok.Data;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class BoardResponse {

    @Data
    public static class ListDTO {
        private Integer id;
        private String title;
        private String name;
        private Timestamp createdAt;
        private String createdAtFormatted;
        private Boolean isOwner;


        public ListDTO(Board board,Integer sessionUserId) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.name = board.getUser().getName();
            this.createdAt = board.getCreatedAt();
            this.createdAtFormatted = board.getCreatedAt()
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            this.isOwner = sessionUserId == board.getUser().getId();;
        }
    }


}

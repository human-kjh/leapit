package com.example.leapit.board;

import com.example.leapit.board.reply.Reply;
import lombok.Data;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Boolean isOwner;
        private Boolean isLike;
        private Integer likeCount;
        private String name;
        private Timestamp createdAt;
        private String createdAtFormatted;
        private Integer likeId;

        private List<ReplyDTO> replies;

        @Data
        public class ReplyDTO {
            private Integer id;
            private String content;
            private String name;
            private Boolean isOwner;

            public ReplyDTO(Reply reply, Integer sessionUserId) {
                this.id = reply.getId();
                this.content = reply.getContent();
                this.name = reply.getUser().getName();
                this.isOwner = reply.getUser().getId().equals(sessionUserId);
            }
        }

        public DetailDTO(Board board, Integer sessionUserId, Boolean isLike, Integer likeCount, Integer likeId, List<Reply> replies) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isOwner = sessionUserId == board.getUser().getId();
            this.name = board.getUser().getName();
            this.createdAt = board.getCreatedAt();
            this.createdAtFormatted = board.getCreatedAt()
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            this.isLike = isLike;
            this.likeCount = likeCount;
            this.likeId = likeId;

            List<ReplyDTO> repliesDTO = new ArrayList<>();
            for (Reply reply : board.getReplies()) {
                ReplyDTO replyDTO = new ReplyDTO(reply, sessionUserId);
                repliesDTO.add(replyDTO);
            }
            this.replies = repliesDTO;
        }
    }
}

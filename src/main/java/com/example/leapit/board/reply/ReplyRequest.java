package com.example.leapit.board.reply;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        private Integer boardId;
        private String content;

        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .content(content)
                    .user(sessionUser)
                    .board(board)
                    .build();
        }
    }

}

package com.example.leapit.board.reply;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "board의 id가 전달되어야 합니다.")
        private Integer boardId;
        @NotEmpty(message = "내용은 필수입니다.")
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

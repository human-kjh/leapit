package com.example.leapit.board.reply;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "게시글 ID는 필수입니다.")
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

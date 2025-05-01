package com.example.leapit.board.like;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class LikeRequest {
    @Data
    public static class SaveDTO {
        @NotNull(message = "게시글 ID는 필수입니다.")
        private Integer boardId;

        public Like toEntity(Integer sessionUserId) {
            return Like.builder()
                    .board(Board.builder().id(boardId).build())
                    .user(User.builder().id(sessionUserId).build())
                    .build();
        }
    }

}

package com.example.leapit.board.like;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class LikeRequest {
    @Data
    public static class SaveDTO {
        @NotEmpty(message = "board의 id가 전달되어야 합니다")
        private Integer boardId;

        public Like toEntity(Integer sessionUserId) {
            return Like.builder()
                    .board(Board.builder().id(boardId).build())
                    .user(User.builder().id(sessionUserId).build())
                    .build();
        }
    }

}

package com.example.leapit.board.like;

import com.example.leapit.board.Board;
import com.example.leapit.user.User;
import lombok.Data;

public class LikeRequest {
    @Data
    public static class SaveDTO {
        private Integer boardId;

        public Like toEntity(Integer sessionUserId) {
            return Like.builder()
                    .board(Board.builder().id(boardId).build())
                    .user(User.builder().id(sessionUserId).build())
                    .build();
        }
    }

}

package com.example.leapit.board;

import com.example.leapit.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "제목은 필수입니다.")
        private String title;
        @NotEmpty(message = "내용은 필수입니다.")
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "제목은 필수입니다.")
        private String title;
        @NotEmpty(message = "내용은 필수입니다.")
        private String content;
    }
}

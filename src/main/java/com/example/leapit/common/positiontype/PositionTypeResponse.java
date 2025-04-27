package com.example.leapit.common.positiontype;

import lombok.Data;

public class PositionTypeResponse {

    @Data
    public static class PositionTypeDTO {
        private String label;

        public PositionTypeDTO(String label) {
            this.label = label;
        }
    }

}

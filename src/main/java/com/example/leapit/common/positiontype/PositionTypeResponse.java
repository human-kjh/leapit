package com.example.leapit.common.positiontype;

import lombok.Data;

public class PositionTypeResponse {

    @Data
    public static class PositionTypeDTO {
        private String label;
        private boolean selected;

        public PositionTypeDTO(String label, boolean selected) {
            this.label = label;
            this.selected = selected;
        }
    }

}

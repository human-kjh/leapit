package com.example.leapit.common.positiontype;

import lombok.Data;

public class PositionTypeResponse {

    @Data
    public static class PositionTypeDTO {
        private String code;
        private boolean selected;

        public PositionTypeDTO(String code, boolean selected) {
            this.code = code;
            this.selected = selected;
        }
    }

    @Data
    public static class PositionTypeListDTO {
        private String code;

        public PositionTypeListDTO(String code, boolean selected) {
            this.code = code;
        }
    }

}

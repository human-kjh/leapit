package com.example.leapit.common.techstack;

import lombok.Data;

public class TechStackResponse {

    @Data
    public static class TechStackDTO {
        private String techStackName;

        public TechStackDTO(String techStackName) {
            this.techStackName = techStackName;
        }
    }

}

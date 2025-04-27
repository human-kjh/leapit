package com.example.leapit.common.region;

import lombok.Data;

public class RegionResponse {

    @Data
    public static class RegionDTO {
        private String region;

        public RegionDTO(String region) {
            this.region = region;
        }
    }
}

package com.example.leapit.common.region;

import lombok.Data;

public class RegionResponse {

    @Data
    public static class RegionDTO {
        private Integer regionId;
        private String region;

        public RegionDTO(Integer regionId, String region) {
            this.regionId = regionId;
            this.region = region;
        }
    }

    @Data
    public static class SubRegionDTO {
        private Integer subRegionId;
        private String subRegion;

        public SubRegionDTO(Integer subRegionId, String subRegion) {
            this.subRegionId = subRegionId;
            this.subRegion = subRegion;
        }
    }
}

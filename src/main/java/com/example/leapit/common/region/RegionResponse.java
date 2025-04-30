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

    @Data
    public static class SelectedRegionDTO {
        private Integer regionId;
        private String region;
        private Boolean selected;

        public SelectedRegionDTO(Integer regionId, String region, Boolean selected) {
            this.regionId = regionId;
            this.region = region;
            this.selected = selected;
        }
    }

    @Data
    public static class SelectedSubRegionDTO {
        private Integer subRegionId;
        private String subRegion;
        private Boolean selected;

        public SelectedSubRegionDTO(Integer subRegionId, String subRegion, Boolean selected) {
            this.subRegionId = subRegionId;
            this.subRegion = subRegion;
            this.selected = selected;
        }
    }

    @Data
    public static class SelectedRegionSubRegionDTO {
        private Integer regionId;
        private String region;
        private Integer subRegionId;
        private String subRegion;
        private Boolean selected;

        public SelectedRegionSubRegionDTO(Integer regionId, String region, Integer subRegionId, String subRegion, Boolean selected) {
            this.regionId = regionId;
            this.region = region;
            this.subRegionId = subRegionId;
            this.subRegion = subRegion;
            this.selected = selected;
        }
    }
}

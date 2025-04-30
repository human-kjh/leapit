package com.example.leapit.resume.etc;

import lombok.Data;

import java.time.LocalDate;

public class EtcResponse {
    @Data
    public static class DetailDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean hasEndDate;
        private String title;
        private String etcType;
        private String institutionName;
        private String description;

        public DetailDTO(Etc etc) {
            this.startDate = etc.getStartDate();
            this.endDate = etc.getEndDate();
            this.hasEndDate = etc.getHasEndDate();
            this.title = etc.getTitle();
            this.etcType = etc.getEtcType();
            this.institutionName = etc.getInstitutionName();
            this.description = etc.getDescription();
        }
    }
}

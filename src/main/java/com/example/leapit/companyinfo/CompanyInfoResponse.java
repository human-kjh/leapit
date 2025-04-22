package com.example.leapit.companyinfo;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

public class CompanyInfoResponse {

    @Data
    public static class DetailDTO{
        private Integer id;
        private String logoImage;
        private String companyName;
        private LocalDate establishmentDate; // 원본 날짜
        private String formattedEstablishmentInfo; // 포맷된 문자열
        private String address;
        private String mainService;
        private String introduction;
        private String image;
        private String benefit;


        public DetailDTO(CompanyInfo companyInfo) {
            this.id = companyInfo.getId();
            this.logoImage = companyInfo.getLogoImage();
            this.companyName = companyInfo.getCompanyName();
            this.establishmentDate = companyInfo.getEstablishmentDate();
            this.formattedEstablishmentInfo = formatEstablishmentInfo(companyInfo.getEstablishmentDate());
            this.address = companyInfo.getAddress();
            this.mainService = companyInfo.getMainService();
            this.introduction = companyInfo.getIntroduction();
            this.image = companyInfo.getImage();
            this.benefit = companyInfo.getBenefit();

        }

        private String formatEstablishmentInfo(LocalDate date) {
            if (date == null) return "정보 없음";

            LocalDate now = LocalDate.now();
            Period period = Period.between(date, now);
            int years = period.getYears();

            String year = String.valueOf(date.getYear());
            String month = String.valueOf(date.getMonthValue());

            return years + "년차 (" + year + "년 " + month + "월 설립)";
        }
    }

}

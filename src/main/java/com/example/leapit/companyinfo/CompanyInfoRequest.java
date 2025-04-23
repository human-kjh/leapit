package com.example.leapit.companyinfo;

import com.example.leapit.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class CompanyInfoRequest {

    @Data
    public static class SaveDTO {
        private String logoImage;
        private String companyName;
        private LocalDate establishmentDate;
        private String address;
        private String mainService;
        private String introduction;
        private String image;
        private String benefit;

        private MultipartFile logoImageFile; // 첨부된 파일
        private MultipartFile imageFile;     // 이미지들


        public CompanyInfo toEntity(User user) {
            return CompanyInfo.builder()
                    .logoImage(logoImage)
                    .companyName(companyName)
                    .establishmentDate(establishmentDate)
                    .address(address)
                    .mainService(mainService)
                    .introduction(introduction)
                    .image(image)
                    .benefit(benefit)
                    .user(user)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        private String logoImage;
        private String companyName;
        private LocalDate establishmentDate;
        private String address;
        private String mainService;
        private String introduction;
        private String image;
        private String benefit;

        private MultipartFile logoImageFile; // 첨부된 파일
        private MultipartFile imageFile;     // 이미지들
    }

}

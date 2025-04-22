package com.example.leapit.companyinfo;

import com.example.leapit.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CompanyInfoService {
    private final CompanyInfoRepository companyInfoRepository;

    @Transactional
    public void save(CompanyInfoRequest.SaveDTO reqDTO, User sessionUser) {
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img/";

        try {
            // 로고 이미지 저장
            if (reqDTO.getLogoImageFile() != null && !reqDTO.getLogoImageFile().isEmpty()) {
                String logoFilename = UUID.randomUUID() + "_" + reqDTO.getLogoImageFile().getOriginalFilename();
                Path logoPath = Paths.get(uploadDir + logoFilename);
                Files.write(logoPath, reqDTO.getLogoImageFile().getBytes());
                reqDTO.setLogoImage(logoFilename);
            }

            // 대표 이미지 저장
            if (reqDTO.getImageFile() != null && !reqDTO.getImageFile().isEmpty()) {
                String imageFilename = UUID.randomUUID() + "_" + reqDTO.getImageFile().getOriginalFilename();
                Path imagePath = Paths.get(uploadDir + imageFilename);
                Files.write(imagePath, reqDTO.getImageFile().getBytes());
                reqDTO.setImage(imageFilename);
            }

        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }

        CompanyInfo companyInfo = reqDTO.toEntity(sessionUser);
        companyInfoRepository.save(companyInfo);
    }
}

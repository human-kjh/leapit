package com.example.leapit.companyinfo;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.JobPostingRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.jobposting.techstack.JobPostingTechStackRepository;
import com.example.leapit.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CompanyInfoService {
    private final CompanyInfoRepository companyInfoRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobPostingTechStackRepository jobPostingTechStackRepository;

    @Transactional
    public CompanyInfo save(CompanyInfoRequest.SaveDTO reqDTO, User sessionUser) {
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
        return companyInfo;
    }

    public CompanyInfoResponse.DetailDTO detail(Integer id, Integer userId) {
        CompanyInfo companyInfo = companyInfoRepository.findById(id);

        // 1. 조인된 결과 가져오기 (JobPosting + JobPostingTechStack)
        List<Object[]> results = jobPostingRepository.findJobPostingsWithTechStacksByUserId(userId);

        // 2. 공고 Map과 기술스택 Map 생성
        Map<Integer, JobPosting> postingMap = new HashMap<>();
        List<JobPostingTechStack> allTechStacks = new ArrayList<>();

        for (Object[] row : results) {
            JobPosting jobPosting = (JobPosting) row[0];
            JobPostingTechStack techStack = (JobPostingTechStack) row[1];

            postingMap.putIfAbsent(jobPosting.getId(), jobPosting);
            if (techStack != null) {
                allTechStacks.add(techStack);
            }
        }

        // 3. Map → List<JobPosting>
        List<JobPosting> jobPostings = new ArrayList<>(postingMap.values());

        // 4. 마감일이 지나지 않은 공고 수 계산
        Long jobPostingCount = jobPostingRepository.countByUserIdAndDeadlineAfter(userId);

        // 5. DTO 생성자 호출
        CompanyInfoResponse.DetailDTO respDTO = new CompanyInfoResponse.DetailDTO(companyInfo, userId, jobPostingCount.intValue(), jobPostings, allTechStacks);
        return respDTO;
    }

    public CompanyInfo updateCheck(Integer id) {
        CompanyInfo companyInfo = companyInfoRepository.findById(id);

        return companyInfo;
    }

    @Transactional
    public CompanyInfo update(Integer id, Integer userId, CompanyInfoRequest.UpdateDTO reqDTO) {
        CompanyInfo companyInfo = companyInfoRepository.findById(id);

        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/img/";

        try {
            if (reqDTO.getLogoImageFile() != null && !reqDTO.getLogoImageFile().isEmpty()) {
                // 새 파일 저장
                String logoFilename = UUID.randomUUID() + "_" + reqDTO.getLogoImageFile().getOriginalFilename();
                Path logoPath = Paths.get(uploadDir + logoFilename);
                Files.write(logoPath, reqDTO.getLogoImageFile().getBytes());
                reqDTO.setLogoImage(logoFilename);
            } else {
                // 기존 값 유지
                reqDTO.setLogoImage(companyInfo.getLogoImage());
            }

            if (reqDTO.getImageFile() != null && !reqDTO.getImageFile().isEmpty()) {
                String imageFilename = UUID.randomUUID() + "_" + reqDTO.getImageFile().getOriginalFilename();
                Path imagePath = Paths.get(uploadDir + imageFilename);
                Files.write(imagePath, reqDTO.getImageFile().getBytes());
                reqDTO.setImage(imageFilename);
            } else {
                reqDTO.setImage(companyInfo.getImage());
            }

        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }

        companyInfo.update(reqDTO.getLogoImage(), reqDTO.getCompanyName(), reqDTO.getEstablishmentDate(), reqDTO.getAddress(), reqDTO.getMainService(), reqDTO.getIntroduction(), reqDTO.getImage(), reqDTO.getBenefit());

        return companyInfo;
    }


    @Transactional
    public void delete(Integer id) {

        companyInfoRepository.deleteById(id);

    }

    public Integer findCompanyInfoIdByUserId(Integer userId) {
        CompanyInfo companyInfo = companyInfoRepository.findByUserId(userId);
        return companyInfo != null ? companyInfo.getId() : null;
    }
}

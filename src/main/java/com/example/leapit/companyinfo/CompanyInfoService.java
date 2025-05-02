package com.example.leapit.companyinfo;

import com.example.leapit._core.error.ex.Exception400;
import com.example.leapit._core.error.ex.Exception403;
import com.example.leapit._core.error.ex.Exception404;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyInfoService {
    private final CompanyInfoRepository companyInfoRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobPostingTechStackRepository jobPostingTechStackRepository;

    @Transactional
    public CompanyInfo save(CompanyInfoRequest.SaveDTO reqDTO, User sessionUser) {
        if (sessionUser == null) throw new Exception404("회원정보가 존재하지 않습니다");
        String uploadDir = System.getProperty("user.dir") + "/upload/";

        if (reqDTO.getLogoImageFile() == null || reqDTO.getLogoImageFile().isEmpty()) {
            throw new Exception400("로고 이미지는 필수입니다.");
        }

        if (reqDTO.getImageFile() == null || reqDTO.getImageFile().isEmpty()) {
            throw new Exception400("대표이미지는 필수입니다.");
        }

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
            throw new Exception400("파일 업로드 실패");
        }


        CompanyInfo companyInfo = reqDTO.toEntity(sessionUser);
        companyInfoRepository.save(companyInfo);
        return companyInfo;
    }

    public CompanyInfoResponse.DetailDTO detail(Integer id, Integer userId) {
        CompanyInfo companyInfo = companyInfoRepository.findById(id);
        if (companyInfo == null) throw new Exception404("기업정보가 존재하지 않습니다.");

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


        // 3. 중복 제거 후 상위 2개 ID만 추출
        List<Integer> top2PostingIds = results.stream()
                .map(row -> ((JobPosting) row[0]).getId())
                .distinct()
                .limit(2)
                .collect(Collectors.toList());


        // 4. 해당 ID에 맞는 JobPosting 리스트만 추출
        List<JobPosting> jobPostings = top2PostingIds.stream()
                .map(postingMap::get)
                .collect(Collectors.toList());


        for (JobPosting jobPosting : jobPostings) {
            // 마감일이 지난 공고는 제외
            if (jobPosting.getDeadline() != null && jobPosting.getDeadline().isBefore(LocalDate.now())) {
                continue;
            }
        }

        // 4. 마감일이 지나지 않은 공고 수 계산
        Long jobPostingCount = jobPostingRepository.countByUserIdAndDeadlineAfter(userId);

        // 5. DTO 생성자 호출
        CompanyInfoResponse.DetailDTO respDTO = new CompanyInfoResponse.DetailDTO(companyInfo, userId, jobPostingCount.intValue(), jobPostings, allTechStacks);
        return respDTO;
    }

    public CompanyInfo updateCheck(Integer id, Integer sessionUserId) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        CompanyInfo companyInfo = companyInfoRepository.findById(id);
        if (companyInfo == null) throw new Exception404("기업정보가 존재하지 않습니다.");
        if (!companyInfo.getUser().getId().equals(sessionUserId)) throw new Exception403("권한이 없습니다.");

        return companyInfo;
    }

    @Transactional
    public CompanyInfo update(Integer id, Integer sessionUserId, CompanyInfoRequest.UpdateDTO reqDTO) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        CompanyInfo companyInfo = companyInfoRepository.findById(id);
        if (companyInfo == null) throw new Exception404("기업정보가 존재하지 않습니다.");

        if (!companyInfo.getUser().getId().equals(sessionUserId)) throw new Exception403("권한이 없습니다.");

        if (reqDTO.getLogoImageFile() == null || reqDTO.getLogoImageFile().isEmpty()) {
            throw new Exception400("로고 이미지는 필수입니다.");
        }

        if (reqDTO.getImageFile() == null || reqDTO.getImageFile().isEmpty()) {
            throw new Exception400("대표이미지는 필수입니다.");
        }

        String uploadDir = System.getProperty("user.dir") + "/upload/";

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
            throw new Exception400("파일 업로드 실패");
        }


        companyInfo.update(reqDTO.getLogoImage(), reqDTO.getCompanyName(), reqDTO.getEstablishmentDate(), reqDTO.getAddress(), reqDTO.getMainService(), reqDTO.getIntroduction(), reqDTO.getImage(), reqDTO.getBenefit());

        return companyInfo;
    }


    // TODO : 기업정보만 삭제시 채용공고의 기업이름으로 인해 터지는 오류 발생 -> 기업 정보 삭제 기능 X 이후 회원 탈퇴로 처리

    public Integer findCompanyInfoIdByUserId(Integer userId) {
        CompanyInfo companyInfo = companyInfoRepository.findByUserId(userId);
        return companyInfo != null ? companyInfo.getId() : null;
    }

    public CompanyInfo findById(Integer id) {
        return companyInfoRepository.findById(id);
    }
}

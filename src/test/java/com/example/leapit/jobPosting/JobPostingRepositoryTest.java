package com.example.leapit.jobPosting;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.JobPostingRepository;
import com.example.leapit.jobposting.JobPostingResponse;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(JobPostingRepository.class)
@DataJpaTest
public class JobPostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Test
    void findAllJobPostingsWithTechStacksByFilter_test() {
        // given
        Integer regionId = 1;
        Integer subRegionId = 1;
        Integer career = 8;
        String techStackCode = "Java";
        String selectedLabel = "백엔드";
        boolean isPopular = true;
        boolean isLatest = false;

        // when
        List<JobPostingResponse.JobPostingDTO> result = jobPostingRepository.findAllJobPostingsWithTechStacksByFilter(
                regionId, subRegionId, career, techStackCode, selectedLabel, isPopular, isLatest
        );

        // then
        System.out.println("조회된 공고 수: " + result.size());
        for (JobPostingResponse.JobPostingDTO dto : result) {
            System.out.println("공고 제목: " + dto.getTitle());
        }
    }

    @Test
    public void findAllJobPostingsWithTechStacks_test() {
        // when
        List<Object[]> results = jobPostingRepository.findAllJobPostingsWithTechStacks();

        // then
        for (Object[] result : results) {
            JobPosting jobPosting = (JobPosting) result[0];
            JobPostingTechStack techStack = (JobPostingTechStack) result[1];

            System.out.println("공고 제목: " + jobPosting.getTitle());
            if (techStack != null) {
                System.out.println("기술 스택: " + techStack.getTechStack().getCode());
                System.out.println();
            } else {
                System.out.println("기술 스택 없음");
            }
            System.out.println("---------------------------");
        }
    }
//
//    @Test
//    public void findAllJobPostingsWithTechStacksByFilter_테스트() {
//        // when
//        List<JobPostingResponse.JobPostingDTO> results = jobPostingRepository.findAllJobPostingsWithTechStacksByFilter();
//
//        // eye
//        System.out.println("-------------------------시스 시작----------------------------");
//        for (JobPostingResponse.JobPostingDTO dto : results) {
//            System.out.println("[공고 카드]");
//            System.out.println("ID: " + dto.getId());
//            System.out.println("회사명: " + dto.getCompanyName());
//            System.out.println("제목: " + dto.getTitle());
//            System.out.println("마감일: " + dto.getDeadline());
//            System.out.println("D-Day: D-" + dto.getDDay());
//            System.out.println("경력: " + dto.getCareer());
//            System.out.println("주소: " + dto.getAddress());
//            System.out.println("이미지: " + dto.getImage());
//
//            System.out.println("기술 스택 목록:");
//            if (dto.getTechStacks() != null && !dto.getTechStacks().isEmpty()) {
//                for (CompanyInfoResponse.DetailDTO.TechStackDTO techStack : dto.getTechStacks()) {
//                    System.out.println("- " + techStack.getName());
//                }
//            } else {
//                System.out.println("- 기술스택 없음");
//            }
//            System.out.println("-----------------------------------------------------");
//        }
//    }

}

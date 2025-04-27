package com.example.leapit.ApplicationRepositoryTest;

import com.example.leapit.application.Application;
import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.application.ApplicationResponse;
import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRepository;
import com.example.leapit.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ApplicationRepository.class)
@DataJpaTest
public class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void applicationRepository_test() {
        // given
        Integer companyUserId = 7;
        Integer jobPostingId = null; // 전체 공고 대상으로 조회
        String passStatus = "합격";
        Boolean isViewed = true;
        Boolean isBookmark = null   ;

        // when
        List<ApplicationResponse.CompanyeApplicantDto> result =
                applicationRepository.findAllApplicantsByFilter(companyUserId, jobPostingId, passStatus, isViewed,isBookmark);

        // eye
        System.out.println("=== 지원자 조회 결과 ===");
        for (ApplicationResponse.CompanyeApplicantDto dto : result) {
            System.out.println("지원자 이름: " + dto.getApplicantName());
            System.out.println("이력서 ID: " + dto.getResumeId());
            System.out.println("지원 포지션: " + dto.getJobTitle());
            System.out.println("지원일: " + dto.getAppliedDate());
            System.out.println("스크랩 여부: " + dto.getIsBookmarked());
            System.out.println("평가 상태: " + dto.getEvaluationStatus());
            System.out.println("열람 여부: "+ dto.getIsViewed());
            System.out.println("북마크 여부: "+ dto.getIsBookmarked());
            System.out.println("-------------------------------");
        }

        System.out.println("총 " + result.size() + "명 조회됨");

    }


    @Test
    public void applicationRepository_test2() {
        // given
        int userId = 2;

        // when
        ApplicationResponse.ApplicationStatusDto a = applicationRepository.findSummaryByUserId(userId);

        System.out.println("------------------테스트 결과 시작----------------------");
        System.out.println("==== 지원 현황 요약 ====");
        System.out.println("총 지원 수: " + a.getTotal());
        System.out.println("합격 수: " + a.getPassed());
        System.out.println("불합격 수: " + a.getFailed());
        System.out.println("------------------테스트 결과 끝----------------------");
    }


}

package com.example.leapit.application;

import com.example.leapit.application.bookmark.ApplicationBookmark;
import com.example.leapit.application.bookmark.ApplicationBookmarkRepository;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import com.example.leapit.resume.ResumeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationBookmarkRepository applicationBookmarkRepository;
    private final ResumeService resumeService;

    public ApplicationResponse.ApplicationBookmarkListDTO myBookmarkpage(Integer userId) {
        // 지원 현황 통계
        ApplicationResponse.ApplicationStatusDto statusDto = applicationRepository.findSummaryByUserId(userId);

        // 스크랩한 공고 목록 조회
        List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> bookmarkListDTO = applicationBookmarkRepository.findAllJobPostingBookmarkByuserId(userId);

        // respDTO에 담기
        ApplicationResponse.ApplicationBookmarkListDTO respDTO = new ApplicationResponse.ApplicationBookmarkListDTO(bookmarkListDTO, statusDto);

        return respDTO;
    }


    public ApplicationResponse.ApplicationListViewDTO myApplicationPage(Integer userId) {
        // 지원 현황 통계
        ApplicationResponse.ApplicationStatusDto statusDto = applicationRepository.findSummaryByUserId(userId);
        // 지원 현황 목록 조회
        List<ApplicationResponse.ApplicationDto> applicationDtos = applicationRepository.findApplicationsByUserId(userId);
        // respDTO에 담기
        ApplicationResponse.ApplicationListViewDTO respDTO = new ApplicationResponse.ApplicationListViewDTO(statusDto, applicationDtos);
        return respDTO;
    }


    public ApplicationResponse.ApplicantListPageDTO findApplicantPageWithFilters(Integer companyUserId, Integer jobPostingId, String passStatus, Boolean isViewed, Boolean isBookmark) {
        // 1. 진행중과 마감된 리스트 조회
        List<ApplicationResponse.IsClosedDTO> positions = applicationRepository.positionAndIsClosedDtoBycompanyUserIds(companyUserId);

        // 2. 지원받은 이력서 목록 조회
        List<ApplicationResponse.CompanyeApplicantDto> applicants = applicationRepository.findAllApplicantsByFilter(companyUserId, jobPostingId, passStatus, isViewed, isBookmark);

        // 3. 선택된 필터 정보 포함한 DTO 구성
        ApplicationResponse.ApplicantListViewDTO listView = new ApplicationResponse.ApplicantListViewDTO(jobPostingId, passStatus, isViewed, isBookmark);


        // 4. pageDTO에 담아서 컨트롤러에 넘김
        ApplicationResponse.ApplicantListPageDTO respDTO = new ApplicationResponse.ApplicantListPageDTO(applicants, positions, listView);


        return respDTO;
    }

    public ApplicationResponse.DetailDTO detail(Integer id) {
        // 지원 id 받아서
        Application application = applicationRepository.findByApplicationId(id);
        // 지원 id -> 이력서 id 찾아서 이력서 전달
        Integer sessionUserId = 6; //TODO : sessionUserId 전달 받기 필요
        ApplicationBookmark bookmark = applicationBookmarkRepository.findByUserIdAndApplicationId(sessionUserId, application.getId());
        boolean isBookmarked = bookmark != null;

        ApplicationResponse.DetailDTO detailDTO = new ApplicationResponse.DetailDTO(application, isBookmarked, resumeService.detail(application.getResume().getId()));
        return detailDTO;
    }

    @Transactional
    public void update(Integer applicationId, ApplicationRequest.UpdateDTO updateDTO) {
        // 해당 지원서 있는지 확인
        Application applicationPS = applicationRepository.findByApplicationId(applicationId);
        if (applicationPS == null) throw new RuntimeException("해당 지원서는 존재하지 않습니다.");

        applicationPS.update(updateDTO.getIsPassed());
    }
}

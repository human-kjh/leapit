package com.example.leapit.application;

import com.example.leapit.application.bookmark.ApplicationBookmarkRepository;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationBookmarkRepository applicationBookmarkRepository;

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

}

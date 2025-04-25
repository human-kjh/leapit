package com.example.leapit.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    // 지원 현황 통계
    public ApplicationResponse.ApplicationStatusDto statusByUserId(Integer userId) {
        return applicationRepository.findSummaryByUserId(userId);
    }

    // 지원 현황 목록 조회
    public List<ApplicationResponse.ApplicationDto> findApplicationsByUserId(Integer userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }

    public ApplicationResponse.ApplicantListPageDTO findApplicantPageWithFilters(Integer companyUserId, Integer jobPostingId, String passStatus, Boolean isViewed, Boolean isBookmark) {
        // 1. 진행중과 마감된 리스트 조회
        List<ApplicationResponse.IsClosedDTO> positions = applicationRepository.positionAndIsClosedDtoBycompanyUserIds(companyUserId);

        // 2. 지원받은 이력서 목록 조회
        List<ApplicationResponse.CompanyeApplicantDto> applicants =applicationRepository.findAllApplicantsByFilter(companyUserId,jobPostingId,passStatus,isViewed, isBookmark);

        // 3. 선택된 필터 정보 포함한 DTO 구성
        ApplicationResponse.ApplicantListViewDTO listView = new ApplicationResponse.ApplicantListViewDTO(jobPostingId, passStatus, isViewed,isBookmark);


        // 4. pageDTO에 담아서 컨트롤러에 넘김
        ApplicationResponse.ApplicantListPageDTO pageDTO = new ApplicationResponse.ApplicantListPageDTO(applicants,positions,listView);


        return pageDTO;
    }
}

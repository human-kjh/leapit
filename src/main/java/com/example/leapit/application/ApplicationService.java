package com.example.leapit.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;



    public List<ApplicationResponse.IsClosedDTO> 진행중과마감포지션조회(Integer companyUserId) {
        return applicationRepository.positionAndIsClosedDtoBycompanyUserIds(companyUserId);
    }

    // 지원 현황 통계
    public ApplicationResponse.ApplicationStatusDto statusByUserId(Integer userId) {
        return applicationRepository.findSummaryByUserId(userId);
    }

    // 지원 현황 목록 조회
    public List<ApplicationResponse.ApplicationDto> getApplicationsByUserId(Integer userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }

//    public List<ApplicationResponse.CompanyeApplicantDto> 지원자목록조회(Integer companyUserId, Integer jobPostingId) {
//        return applicationRepository.findAllApplicantsByCompanyUserId(companyUserId,jobPostingId);
//    }

    public ApplicationResponse.ApplicantListPageDTO 기업지원현황페이지조회(Integer companyUserId, Integer jobPostingId,String passStatus) {
        // 1. 진행중과 마감된 리스트 조회
        List<ApplicationResponse.IsClosedDTO> positions = applicationRepository.positionAndIsClosedDtoBycompanyUserIds(companyUserId);

        // 2. 지원받은 이력서 목록 조회
        List<ApplicationResponse.CompanyeApplicantDto> applicants =applicationRepository.findAllApplicantsByCompanyUserId(companyUserId,jobPostingId,passStatus);

        // 3. 선택된 필터 정보 포함한 DTO 구성
        ApplicationResponse.ApplicantListViewDTO listView = new ApplicationResponse.ApplicantListViewDTO(jobPostingId, passStatus);

        // 4. pageDTO에 담아서 컨트롤러에 넘김
        ApplicationResponse.ApplicantListPageDTO pageDTO = new ApplicationResponse.ApplicantListPageDTO(applicants,positions,listView);
        return pageDTO;
    }
}

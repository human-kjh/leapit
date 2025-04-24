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

    public List<ApplicationResponse.CompanyeApplicantDto> 지원자목록조회(Integer companyUserId, Integer jobPostingId) {
        return applicationRepository.findAllApplicantsByCompanyUserId(companyUserId,jobPostingId);
    }
}

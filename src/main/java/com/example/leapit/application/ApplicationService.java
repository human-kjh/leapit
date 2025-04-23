package com.example.leapit.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    // 지원 현황 통계
    public ApplicationResponse.ApplicationSummaryDto getSummaryByUserId(Integer userId) {
        return applicationRepository.findSummaryByUserId(userId);
    }

    // 지원 현황 목록 조회
    public List<ApplicationResponse.ApplicationDto> getApplicationsByUserId(Integer userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }
}

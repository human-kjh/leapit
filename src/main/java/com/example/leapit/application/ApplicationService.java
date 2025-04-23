package com.example.leapit.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationResponse.ApplicationSummaryDto getSummaryByUserId(Integer userId) {
        return applicationRepository.findSummaryByUserId(userId);
    }

    public List<ApplicationResponse.ApplicationDto> getApplicationsByUserId(Integer userId) {
        return applicationRepository.findApplicationsByUserId(userId);
    }
}

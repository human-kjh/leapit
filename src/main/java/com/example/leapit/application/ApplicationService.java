package com.example.leapit.application;

import com.example.leapit.resume.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;


    public ApplicationResponse.ApplicationListViewDTO findApplicationListByUserId(Integer userId) {
        // 지원 현황 통계
        ApplicationResponse.ApplicationStatusDto statusDto = applicationRepository.findSummaryByUserId(userId);
        // 지원 현황 목록 조회
        List<ApplicationResponse.ApplicationDto> applicationDtos = applicationRepository.findApplicationsByUserId(userId);
        // viewDTO에 담기
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

    public ApplicationRequest.ApplyFormDTO getApplyForm(Integer jobPostingId, Integer userId) {
        return applicationRepository.findApplyFormInfo(jobPostingId, userId);
    }

    public ApplicationRequest.JobPostingInfoDto getJobPostingInfo(Integer jobPostingId) {
        return applicationRepository.findJobPostingInfoDto(jobPostingId);
    }

    public ApplicationRequest.AvailableResumeDTO getAvailableResumeInfo(Integer resumeId) {
        Resume resume = applicationRepository.findResumeById(resumeId);
        if (resume == null) {
            return null; // 또는 예외 처리
        }
        return new ApplicationRequest.AvailableResumeDTO(resume);
    }

    // 특정 사용자의 모든 AvailableResumeDTO 목록 조회 (예시 - 스트림 없이)
    public List<ApplicationRequest.AvailableResumeDTO> getAllAvailableResumes(Integer userId) {
        // 레포지토리에서 해당 사용자의 모든 이력서 조회
        List<Resume> resumes = applicationRepository.findAllResumesByUserId(userId);
        List<ApplicationRequest.AvailableResumeDTO> availableResumes = new ArrayList<>();
        for (Resume resume : resumes) {
            availableResumes.add(new ApplicationRequest.AvailableResumeDTO(resume));
        }
        return availableResumes;
    }
}

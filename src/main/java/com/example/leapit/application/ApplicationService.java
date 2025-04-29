package com.example.leapit.application;

import com.example.leapit.application.bookmark.ApplicationBookmark;
import com.example.leapit.application.bookmark.ApplicationBookmarkRepository;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.JobPostingRepository;
import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRepository;
import com.example.leapit.resume.ResumeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationBookmarkRepository applicationBookmarkRepository;
    private final ResumeService resumeService;
    private final JobPostingRepository jobPostingRepository;
    private final ResumeRepository resumeRepository;

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

    public ApplicationRequest.ApplyFormDTO getApplyForm(Integer jobPostingId, Integer userId) {
        ApplicationRequest.ApplyFormDTO applyFormDTO = applicationRepository.findApplyFormInfo(jobPostingId, userId);
        return applyFormDTO;
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

    // 특정 사용자의 모든 AvailableResumeDTO 목록 조회
    public List<ApplicationRequest.AvailableResumeDTO> getAllAvailableResumes(Integer userId) {
        // 레포지토리에서 해당 사용자의 모든 이력서 조회
        List<Resume> resumes = applicationRepository.findAllResumesByUserId(userId);
        List<ApplicationRequest.AvailableResumeDTO> availableResumes = new ArrayList<>();
        for (Resume resume : resumes) {
            availableResumes.add(new ApplicationRequest.AvailableResumeDTO(resume));
        }
        return availableResumes;
    }

    @Transactional
    public void apply(ApplicationRequest.ApplyReqDTO applyReqDTO, Integer userId) {

        boolean alreadyApplied = applicationRepository.checkIfAlreadyApplied(userId, applyReqDTO.getJobPostingId());

        if (alreadyApplied) {
            throw new RuntimeException("이미 지원한 공고입니다.");
        }

        Resume resume = resumeRepository.findById(applyReqDTO.getResumeId());
        JobPosting jobPosting = jobPostingRepository.findById(applyReqDTO.getJobPostingId());

        Application application = applyReqDTO.toEntity(resume, jobPosting);

        applicationRepository.save(application);
    }

}
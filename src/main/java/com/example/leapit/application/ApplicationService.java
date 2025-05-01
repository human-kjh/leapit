package com.example.leapit.application;

import com.example.leapit._core.error.ex.*;
import com.example.leapit.application.bookmark.ApplicationBookmark;
import com.example.leapit.application.bookmark.ApplicationBookmarkRepository;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.JobPostingRepository;
import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRepository;
import com.example.leapit.resume.ResumeResponse;
import com.example.leapit.resume.ResumeService;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
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
    private final UserRepository userRepository;

    public ApplicationResponse.ApplicationBookmarkListDTO myBookmarkpage(Integer userId) {
        if (userId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        // 지원 현황 통계
        ApplicationResponse.ApplicationStatusDto statusDto = applicationRepository.findSummaryByUserId(userId);

        // 스크랩한 공고 목록 조회
        List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> bookmarkListDTO = applicationBookmarkRepository.findAllJobPostingBookmarkByuserId(userId);

        // respDTO에 담기
        ApplicationResponse.ApplicationBookmarkListDTO respDTO = new ApplicationResponse.ApplicationBookmarkListDTO(bookmarkListDTO, statusDto);

        return respDTO;
    }


    public ApplicationResponse.ApplicationListViewDTO myApplicationPage(Integer userId) {
        if (userId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        // 지원 현황 통계
        ApplicationResponse.ApplicationStatusDto statusDto = applicationRepository.findSummaryByUserId(userId);
        // 지원 현황 목록 조회
        List<ApplicationResponse.ApplicationDto> applicationDtos = applicationRepository.findApplicationsByUserId(userId);
        // respDTO에 담기
        ApplicationResponse.ApplicationListViewDTO respDTO = new ApplicationResponse.ApplicationListViewDTO(statusDto, applicationDtos);
        return respDTO;
    }


    public ApplicationResponse.ApplicantListPageDTO findApplicantPageWithFilters(Integer companyUserId, Integer jobPostingId, String passStatus, Boolean isViewed, Boolean isBookmark) {
        if (companyUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

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

    public ApplicationResponse.DetailDTO detail(Integer id, Integer sessionUserId) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        // 지원 id 받아서
        Application application = applicationRepository.findByApplicationId(id);
        if (application == null) throw new Exception404("지원 내역을 찾을 수 없습니다.");

        // 북마크 여부 조회
        ApplicationBookmark bookmark = applicationBookmarkRepository.findByUserIdAndApplicationId(sessionUserId, application.getId());
        boolean isBookmarked = bookmark != null;

        // 이력서 상세 포함한 DTO 조립
        ResumeResponse.DetailDTO resumeDetail = resumeService.detail(application.getResume().getId(), sessionUserId);
        return new ApplicationResponse.DetailDTO(application, isBookmarked, resumeDetail);
    }

    @Transactional
    public void update(Integer applicationId, ApplicationRequest.UpdateDTO updateDTO, Integer sessionUserId) {
        if (sessionUserId == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");

        // 해당 지원서 있는지 확인
        Application applicationPS = applicationRepository.findByApplicationId(applicationId);
        if (applicationPS == null) throw new ExceptionApi404("해당 지원서는 존재하지 않습니다.");

        // 권한 확인
        User user = applicationPS.getResume().getUser();
        if (!user.getId().equals(sessionUserId)) throw new ExceptionApi403("권한이 없습니다.");


        applicationPS.update(updateDTO.getIsPassed());
    }

    public ApplicationRequest.ApplyFormDTO getApplyForm(Integer jobPostingId, Integer userId) {
        if (userId == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");

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
        if (userId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        boolean alreadyApplied = applicationRepository.checkIfAlreadyApplied(userId, applyReqDTO.getJobPostingId());
        if (alreadyApplied) {
            throw new Exception400("이미 지원한 공고입니다.");
        }

        Resume resume = resumeRepository.findById(applyReqDTO.getResumeId());
        if (resume == null) throw new Exception404("이력서가 존재하지 않습니다.");

        User user = userRepository.findById(userId);
        if (!user.getId().equals(resume.getUser().getId())) throw new Exception403("권한이 없습니다.");

        JobPosting jobPosting = jobPostingRepository.findById(applyReqDTO.getJobPostingId());
        if (jobPosting == null) throw new Exception404("채용공고가 존재하지 않습니다.");

        Application application = applyReqDTO.toEntity(resume, jobPosting);

        applicationRepository.save(application);
    }

}
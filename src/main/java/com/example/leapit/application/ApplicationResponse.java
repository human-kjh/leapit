package com.example.leapit.application;

import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import com.example.leapit.resume.ResumeResponse;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

public class ApplicationResponse {


    @Data
    public static class ApplicantListPageDTO {
        private List<CompanyeApplicantDto> applicants;
        private List<IsClosedDTO> positions;
        private ApplicantListViewDTO listView;



        public ApplicantListPageDTO(List<CompanyeApplicantDto> applicants,
                                    List<IsClosedDTO> positions,
                                    ApplicantListViewDTO listView) {
            this.applicants = applicants;
            this.positions = positions;
            if (listView == null) {
                this.listView = new ApplicantListViewDTO(null, "전체", null,null);
            } else {
                this.listView = listView;
            };
        }
    }

    @Data
    public static class ApplicantListViewDTO {
        private Integer selectedJobPostingId;
        private String passStatus;
        private Boolean is합격선택;
        private Boolean is불합격선택;
        private Boolean is전체선택;
        private Boolean isViewed;
        private Boolean isBookmark;

        public ApplicantListViewDTO(Integer jobPostingId, String passStatus, Boolean isViewed,Boolean isBookmark) {
            this.selectedJobPostingId = jobPostingId;
            this.passStatus = passStatus;
            this.isViewed = isViewed;
            this.isBookmark = isBookmark;

            this.is합격선택 = "합격".equals(passStatus);
            this.is불합격선택 = "불합격".equals(passStatus);
            this.is전체선택 = !"합격".equals(passStatus) && !"불합격".equals(passStatus); // default: 전체
        }
    }

    @Data
    public static class IsClosedDTO {
        private Integer jobPostingId;
        private String title;
        private Boolean isClosed;

        public IsClosedDTO(Integer jobPostingId, String title, Boolean isClosed) {
            this.jobPostingId = jobPostingId;
            this.title = title;
            this.isClosed = isClosed;
        }
    }



    @Data
    public static class CompanyeApplicantDto {
        private List<IsClosedDTO> positions;
        private Integer applicationId;
        private Integer resumeId;      // 지원 이력서 ID 이력서 조회 주소에 넣으면 됨
        private String applicantName;       // 지원자 이름 (resume.user.name)
        private String jobTitle;            // 공고명 (job_posting.title)
        private LocalDate appliedDate;      // 지원일 (application.applied_date)
        private Boolean isBookmarked;       // 북마크 여부 (application_bookmark 존재 여부)
        private String evaluationStatus;    // 평가 상태 ('미열람' / '열람' / '합격' / '불합격')
        private Boolean isViewed;

        public CompanyeApplicantDto(Integer applicationId,
                                    Integer resumeId,
                                    String applicantName,
                                    String jobTitle,
                                    LocalDate appliedDate,
                                    Boolean isBookmarked,
                                    String evaluationStatus,
                                    Boolean isViewed) {
            this.applicationId = applicationId;
            this.resumeId = resumeId;
            this.applicantName = applicantName;
            this.jobTitle = jobTitle;
            this.appliedDate = appliedDate;
            this.isBookmarked = isBookmarked;
            this.evaluationStatus = evaluationStatus;
            this.isViewed = isViewed;
        }
    }

    @Data
    public static class ApplicationBookmarkListDTO {
        private List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> bookmarks;
        private ApplicationStatusDto status;

        public ApplicationBookmarkListDTO(List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> bookmarks, ApplicationStatusDto status) {
            this.bookmarks = bookmarks;
            this.status = status;
        }
    }

    // 지원 현황 목록 + 통계
    @Data
    public static class ApplicationListViewDTO {
        private ApplicationStatusDto status;
        private List<ApplicationDto> applications;
        public ApplicationListViewDTO(ApplicationStatusDto status, List<ApplicationDto> applications) {
            this.status = status;
            this.applications = applications;
        }
    }

    // 지원 형황 목록
    @Data
    public static class ApplicationDto {
        private String companyName;
        private String jobTitle;
        private LocalDate appliedDate;
        private Integer resumeId;
        private Integer jobPostingId;

        public ApplicationDto(String companyName, String jobTitle, LocalDate appliedDate,
                              Integer resumeId, Integer jobPostingId) {
            this.companyName = companyName;
            this.jobTitle = jobTitle;
            this.appliedDate = appliedDate;
            this.resumeId = resumeId;
            this.jobPostingId = jobPostingId;
        }
    }

    // 지원 현황 통계
    @Data
    public static class ApplicationStatusDto {
        private Long total;
        private Long passed;
        private Long failed;

        public ApplicationStatusDto(Long total, Long passed, Long failed) {
            this.total = total;
            this.passed = passed;
            this.failed = failed;
        }
    }

    @Data
    public static class DetailDTO{
        private Integer id; // 지원id
        private Boolean isBookmarked;
        private Boolean isPassed;
        private String jobPositionTitle;
        private ResumeResponse.DetailDTO detailDTO;

        private String isPassedString;

        public DetailDTO(Application application, Boolean isBookmarked, ResumeResponse.DetailDTO detailDTO) {
            this.id = application.getId();
            this.isBookmarked = isBookmarked;
            this.isPassed = application.getIsPassed();
            this.jobPositionTitle = application.getJobPosting().getTitle();
            this.detailDTO = detailDTO;

            this.isPassedString = (application.getIsPassed() == null)
                    ? "null"
                    : application.getIsPassed().toString();
        }
    }
}


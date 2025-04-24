package com.example.leapit.application;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

public class ApplicationResponse {

    @Data
    public static class ApplicantListPageDTO {
        private List<CompanyeApplicantDto> applicants;
        private List<IsClosedDTO> positions;

        public ApplicantListPageDTO(List<CompanyeApplicantDto> applicants,
                                    List<IsClosedDTO> positions) {
            this.applicants = applicants;
            this.positions = positions;
        }
    }

    @Data
    public static class ApplicantListViewDTO {
        private Integer selectedJobPostingId;
        private String passStatus;
        private Boolean is합격선택;
        private Boolean is불합격선택;
        private Boolean is전체선택;

        public ApplicantListViewDTO(Integer jobPostingId, String passStatus) {
            this.selectedJobPostingId = jobPostingId;
            this.passStatus = passStatus;

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

        public CompanyeApplicantDto(Integer applicationId,
                                    Integer resumeId,
                                    String applicantName,
                                    String jobTitle,
                                    LocalDate appliedDate,
                                    Boolean isBookmarked,
                                    String evaluationStatus) {
            this.applicationId = applicationId;
            this.resumeId = resumeId;
            this.applicantName = applicantName;
            this.jobTitle = jobTitle;
            this.appliedDate = appliedDate;
            this.isBookmarked = isBookmarked;
            this.evaluationStatus = evaluationStatus;
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



}


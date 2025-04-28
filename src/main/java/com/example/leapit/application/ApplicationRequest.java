package com.example.leapit.application;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.resume.Resume;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.user.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRequest {

    @Data
    public static class ApplicantListReqDTO {
        private Integer jobPostingId;
        private String jobPosition;
        private Boolean isClosed;
        private Boolean isViewed;
        private Boolean isBookmark;
    }

    @Data
    public static class ApplyReqDTO {
        private Integer jobPostingId;
        private Integer resumeId;
    }

    @Data
    public static class ApplyFormDTO {
        private Integer jobPostingId;
        private String jobpostingName;
        private String companyName;

        private String userName;
        private String userEmail;
        private String phoneNumber;
        private LocalDate birthDate;

        private List<AvailableResumeDTO> availableResumes; // 선택 가능한 이력서 목록

        public ApplyFormDTO(Integer jobPostingId, String jobpostingName, String companyName, User user, List<Resume> resumes) {
            this.jobPostingId = jobPostingId;
            this.jobpostingName = jobpostingName;
            this.companyName = companyName;
            this.userName = user.getName();
            this.userEmail = user.getEmail();
            this.phoneNumber = user.getContactNumber();
            this.birthDate = user.getBirthDate();

            this.availableResumes = new ArrayList<>();
            for (Resume resume : resumes) {
                this.availableResumes.add(new AvailableResumeDTO(resume));
            }
        }
    }

    @Data
    public static class AvailableResumeDTO {
        private Integer resumeId;
        private String resumeTitle;
        private List<String> skills; // 기술 스택
        private String education; // 학력
        private String experience; // 경력
        private LocalDate registeredDate; // 등록일

        public AvailableResumeDTO(Resume resume) {
            this.resumeId = resume.getId();
            this.resumeTitle = resume.getTitle();
            this.registeredDate = resume.getCreatedAt().toLocalDateTime().toLocalDate();

            // 기술 스택 추출
            this.skills = new ArrayList<>();
            for (ResumeTechStack techStackEntry : resume.getResumeTechStacks()) {
                this.skills.add(techStackEntry.getTechStack());
            }

            // 학력 추출
            this.education = "";
            List<Education> educations = resume.getEducations();
            Education latestEducation = null;
            for (Education education : educations) {
                if (latestEducation == null || (education.getGraduationDate() != null &&
                        (latestEducation.getGraduationDate() == null || education.getGraduationDate().isAfter(latestEducation.getGraduationDate())))) {
                    latestEducation = education;
                } else if (latestEducation == null && education.getGraduationDate() != null) {
                    latestEducation = education;
                }
            }
            if (latestEducation != null) {
                this.education = latestEducation.getSchoolName() + " " + latestEducation.getMajor() + " (" + latestEducation.getEducationLevel() + ")";
            } else if (!educations.isEmpty()) {
                Education latestEnrollment = null;
                for (Education education : educations) {
                    if (latestEnrollment == null || education.getCreatedAt().after(latestEnrollment.getCreatedAt())) {
                        latestEnrollment = education;
                    }
                }
                if (latestEnrollment != null) {
                    this.education = latestEnrollment.getSchoolName() + " " + latestEnrollment.getMajor() + " (" + latestEnrollment.getEducationLevel() + ")";
                }
            }

            // 경력 추출
            this.experience = resume.getExperiences().isEmpty() ? "" : resume.getExperiences().get(0).getCompanyName();
        }
    }

    @Data
    public static class JobPostingInfoDto {
        private Integer jobPostingId;
        private String title;
        private String companyName;
        private List<String> techStacks;

        public JobPostingInfoDto(JobPosting jobPosting) {
            this.jobPostingId = jobPosting.getId();
            this.title = jobPosting.getTitle();
            this.companyName = jobPosting.getUser().getName();
            this.techStacks = new ArrayList<>();
            for (JobPostingTechStack techStackEntry : jobPosting.getJobPostingTechStacks()) {
                this.techStacks.add(techStackEntry.getTechStack().getCode());
            }
        }
    }
}
package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class JobPostingRequest {

    // 채용공고 등록 요청 DTO
    @Data
    public static class SaveDTO {
        private String title;
        private String positionType;
        private Integer minCareerLevel;
        private Integer maxCareerLevel;
        private String educationLevel;
        private Integer addressRegionId;
        private Integer addressSubRegionId;
        private String addressDetail;
        private String serviceIntro;
        private LocalDate deadline;
        private String responsibility;
        private String qualification;
        private String preference;
        private String benefit;
        private String additionalInfo;
        private List<String> techStackIds;

        public JobPosting toEntity(User user) {
            return JobPosting.builder()
                    .user(user)
                    .title(title)
                    .positionType(positionType)
                    .minCareerLevel(minCareerLevel)
                    .maxCareerLevel(maxCareerLevel)
                    .educationLevel(educationLevel)
                    .addressRegionId(addressRegionId)
                    .addressSubRegionId(addressSubRegionId)
                    .addressDetail(addressDetail)
                    .serviceIntro(serviceIntro)
                    .deadline(deadline)
                    .responsibility(responsibility)
                    .qualification(qualification)
                    .preference(preference)
                    .benefit(benefit)
                    .additionalInfo(additionalInfo)
                    .build();
        }
    }

    // 채용공고 수정 요청 DTO
    @Builder
    @Data
    public static class UpdateDTO {
        private String title;
        private String positionType;
        private Integer minCareerLevel;
        private Integer maxCareerLevel;
        private String educationLevel;
        private Integer addressRegionId;
        private Integer addressSubRegionId;
        private String addressDetail;
        private String serviceIntro;
        private LocalDate deadline;
        private String responsibility;
        private String qualification;
        private String preference;
        private String benefit;
        private String additionalInfo;
        private List<String> techStackIds;

        public JobPosting toEntity(JobPosting jobPosting) {
            jobPosting.setTitle(title);
            jobPosting.setPositionType(positionType);
            jobPosting.setMinCareerLevel(minCareerLevel);
            jobPosting.setMaxCareerLevel(maxCareerLevel);
            jobPosting.setEducationLevel(educationLevel);
            jobPosting.setAddressRegionId(addressRegionId);
            jobPosting.setAddressSubRegionId(addressSubRegionId);
            jobPosting.setAddressDetail(addressDetail);
            jobPosting.setServiceIntro(serviceIntro);
            jobPosting.setDeadline(deadline);
            jobPosting.setResponsibility(responsibility);
            jobPosting.setQualification(qualification);
            jobPosting.setPreference(preference);
            jobPosting.setBenefit(benefit);
            jobPosting.setAdditionalInfo(additionalInfo);
            return jobPosting;
        }
    }
}
package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JobPostingRequest {
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
    private List<String> techStack; // 추가된 기술 스택 필드

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
                .techStack(techStack) // 빌더에 기술 스택 추가
                .build();
    }

}
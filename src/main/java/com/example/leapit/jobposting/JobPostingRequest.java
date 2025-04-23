package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JobPostingRequest {

    // SaveDTO: 채용 공고를 등록할 때 클라이언트로부터 전달받는 데이터를 담는 내부 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    public static class SaveDTO {
        private String title;             // 채용 공고 제목
        private String positionType;      // 직무 유형
        private Integer minCareerLevel;   // 최소 경력
        private Integer maxCareerLevel;   // 최대 경력
        private String educationLevel;    // 학력 요구사항
        private Integer addressRegionId;  // 주소 (지역 ID)
        private Integer addressSubRegionId; // 주소 (세부 지역 ID)
        private String addressDetail;     // 상세 주소
        private String serviceIntro;      // 서비스 소개
        private LocalDate deadline;       // 마감일
        private String responsibility;    // 주요 업무
        private String qualification;     // 자격 요건
        private String preference;        // 우대 조건
        private String benefit;           // 복지 혜택
        private String additionalInfo;    // 기타 정보
        private List<String> techStack;   // 기술 스택 목록

        // toEntity 메서드: SaveDTO에 담긴 데이터를 JobPosting 엔티티 객체로 변환하는 메서드
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
                    .techStack(techStack)
                    .build();
        }
    }


    // UpdateDTO: 채용 공고를 수정할 때 클라이언트로부터 전달받는 데이터를 담는 내부 클래스
    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateDTO {
        private String title;             // 채용 공고 제목
        private String positionType;      // 직무 유형
        private Integer minCareerLevel;   // 최소 경력
        private Integer maxCareerLevel;   // 최대 경력
        private String educationLevel;    // 학력 요구사항
        private Integer addressRegionId;  // 주소 (지역 ID)
        private Integer addressSubRegionId; // 주소 (세부 지역 ID)
        private String addressDetail;     // 상세 주소
        private String serviceIntro;      // 서비스 소개
        private LocalDate deadline;       // 마감일
        private String responsibility;    // 주요 업무
        private String qualification;     // 자격 요건
        private String preference;        // 우대 조건
        private String benefit;           // 복지 혜택
        private String additionalInfo;    // 기타 정보
        private List<String> techStack;   // 기술 스택 목록

        // UpdateDTO 생성자: JobPosting 엔티티 객체를 파라미터로 받아서 UpdateDTO의 필드 값을 초기화
        // 수정 폼에 기존 데이터를 보여주기 위해 사용
        public UpdateDTO(JobPosting jobPosting) {
            this.title = jobPosting.getTitle();
            this.positionType = jobPosting.getPositionType();
            this.minCareerLevel = jobPosting.getMinCareerLevel();
            this.maxCareerLevel = jobPosting.getMaxCareerLevel();
            this.educationLevel = jobPosting.getEducationLevel();
            this.addressRegionId = jobPosting.getAddressRegionId();
            this.addressSubRegionId = jobPosting.getAddressSubRegionId();
            this.addressDetail = jobPosting.getAddressDetail();
            this.serviceIntro = jobPosting.getServiceIntro();
            this.deadline = jobPosting.getDeadline();
            this.responsibility = jobPosting.getResponsibility();
            this.qualification = jobPosting.getQualification();
            this.preference = jobPosting.getPreference();
            this.benefit = jobPosting.getBenefit();
            this.additionalInfo = jobPosting.getAdditionalInfo();
            this.techStack = jobPosting.getTechStack();
        }

        // toEntity 메서드: UpdateDTO에 담긴 데이터를 기존 JobPosting 객체에 반영하는 메서드
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
            jobPosting.setTechStack(techStack);
            return jobPosting;
        }
    }

}
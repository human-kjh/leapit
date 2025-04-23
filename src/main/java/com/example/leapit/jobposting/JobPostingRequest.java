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
        // User 객체를 파라미터로 받아서 채용 공고를 등록하는 사용자를 설정
        public JobPosting toEntity(User user) {
            return JobPosting.builder()
                    .user(user)             // 채용 공고를 등록한 사용자
                    .title(title)           // 제목
                    .positionType(positionType) // 직무 유형
                    .minCareerLevel(minCareerLevel) // 최소 경력
                    .maxCareerLevel(maxCareerLevel) // 최대 경력
                    .educationLevel(educationLevel) // 학력 요구사항
                    .addressRegionId(addressRegionId) // 주소 (지역 ID)
                    .addressSubRegionId(addressSubRegionId) // 주소 (세부 지역 ID)
                    .addressDetail(addressDetail) // 상세 주소
                    .serviceIntro(serviceIntro) // 서비스 소개
                    .deadline(deadline)     // 마감일
                    .responsibility(responsibility) // 주요 업무
                    .qualification(qualification) // 자격 요건
                    .preference(preference)   // 우대 조건
                    .benefit(benefit)         // 복지 혜택
                    .additionalInfo(additionalInfo) // 기타 정보
                    .techStack(techStack)     // 기술 스택 목록
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
    }
}
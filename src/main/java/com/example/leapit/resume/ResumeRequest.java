package com.example.leapit.resume;

import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.project.Project;
import com.example.leapit.resume.project.techstack.ProjectTechStack;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.training.Training;
import com.example.leapit.resume.training.techstack.TrainingTechStack;
import com.example.leapit.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ResumeRequest {

    @Data
    public static class UpdateDTO {
        private String title;
        private String photoUrl;
        private Boolean isPublic;
        private String summary;
        @NotEmpty(message = "직무를 선택해주세요.")
        private String positionType;
        @NotEmpty(message = "자기소개서를 작성해주세요.")
        private String selfIntroduction;

        @NotEmpty(message = "기술 스택은 최소 1개 이상 선택해야 합니다.")
        @Valid
        private List<String> resumeTechStacks;

        @NotEmpty(message = "학력 정보를 1개 이상 입력해주세요.")
        @Valid
        private List<EducationDTO> educations;
        @NotEmpty(message = "프로젝트는 최소 1개 이상 작성해주세요.")
        @Valid
        private List<ProjectDTO> projects;
        private List<ExperienceDTO> experiences;
        private List<LinkDTO> links;
        private List<TrainingDTO> trainings;
        private List<EtcDTO> etcs;

        @Data
        public static class EducationDTO {
            private Integer id;
            @NotNull(message = "졸업일은 필수입니다.")
            private LocalDate graduationDate;
            private Boolean isDropout;
            @NotEmpty(message = "학력 수준은 필수입니다.")
            private String educationLevel;
            @NotEmpty(message = "학교명은 필수입니다.")
            private String schoolName;
            private String major;
            private BigDecimal gpa;
            private BigDecimal gpaScale;
        }

        @Data
        public static class ProjectDTO {
            private Integer id;
            @NotNull(message = "프로젝트 시작일은 필수입니다.")
            private LocalDate startDate;
            @NotNull(message = "프로젝트 종료일은 필수입니다.")
            private LocalDate endDate;
            private Boolean isOngoing;
            @NotEmpty(message = "프로젝트 제목은 필수입니다.")
            private String title;
            private String summary;
            @NotEmpty(message = "프로젝트 설명은 필수입니다.")
            private String description;
            private String repositoryUrl;
            @NotEmpty(message = "기술 스택은 최소 1개 이상 선택해야 합니다.")
            @Valid
            private List<String> techStacks;
        }

        @Data
        public static class ExperienceDTO {
            private Integer id;
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isEmployed;
            private String companyName;
            private String summary;
            private String position;
            private String responsibility;
            private List<String> techStacks;
        }

        @Data
        public static class LinkDTO {
            private Integer id;
            private String title;
            private String url;
        }

        @Data
        public static class TrainingDTO {
            private Integer id;
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isOngoing;
            private String courseName;
            private String institutionName;
            private String description;
            private List<String> techStacks;
        }

        @Data
        public static class EtcDTO {
            private Integer id;
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean hasEndDate;
            private String title;
            private String etcType;
            private String institutionName;
            private String description;
        }
    }


    // SaveDTO ----------------------------------------------------------------------------------------------------
    @Data
    public static class SaveDTO {
        private String title;
        private String photoUrl;

        private Boolean isPublic;
        private String summary;
        @NotEmpty(message = "직무를 선택해주세요.")
        private String positionType;

        @NotEmpty(message = "자기소개서를 작성해주세요.")
        private String selfIntroduction;

        @NotEmpty(message = "기술 스택은 최소 1개 이상 선택해야 합니다.")
        @Valid
        private List<String> resumeTechStacks;

        @NotEmpty(message = "학력 정보를 1개 이상 입력해주세요.")
        @Valid
        private List<EducationDTO> educations;

        @NotEmpty(message = "프로젝트는 최소 1개 이상 작성해주세요.")
        @Valid
        private List<ProjectDTO> projects;
        private List<ExperienceDTO> experiences;
        private List<LinkDTO> links;
        private List<TrainingDTO> trainings;
        private List<EtcDTO> etcs;

        @Data
        public static class EducationDTO {
            private Integer id;

            @NotNull(message = "졸업일은 필수입니다.")
            private LocalDate graduationDate;
            private Boolean isDropout;

            @NotEmpty(message = "학력 수준은 필수입니다.")
            private String educationLevel;

            @NotEmpty(message = "학교명은 필수입니다.")
            private String schoolName;
            private String major;
            private BigDecimal gpa;
            private BigDecimal gpaScale;
        }

        @Data
        public static class ProjectDTO {
            @NotNull(message = "프로젝트 시작일은 필수입니다.")
            private LocalDate startDate;

            @NotNull(message = "프로젝트 종료일은 필수입니다.")
            private LocalDate endDate;

            private Boolean isOngoing;

            @NotEmpty(message = "프로젝트 제목은 필수입니다.")
            private String title;
            private String summary;

            @NotEmpty(message = "프로젝트 설명은 필수입니다.")
            private String description;

            private String repositoryUrl;

            @NotEmpty(message = "기술 스택은 최소 1개 이상 입력해야 합니다.")
            @Valid
            private List<String> techStacks;
        }

        @Data
        public static class ExperienceDTO {
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isEmployed;
            private String companyName;
            private String summary;
            private String position;
            private String responsibility;
            private List<String> techStacks;
        }

        @Data
        public static class LinkDTO {
            private String title;
            private String url;
        }

        @Data
        public static class TrainingDTO {
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isOngoing;
            private String courseName;
            private String institutionName;
            private String description;
            private Timestamp createdAt;
            private List<String> techStacks;
        }

        @Data
        public static class EtcDTO {
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean hasEndDate;
            private String title;
            private String etcType;
            private String institutionName;
            private String description;

        }

        public Resume toEntity(User user) {
            Resume resume = Resume.builder()
                    .title(this.title)
                    .photoUrl(this.photoUrl)
                    .isPublic(this.isPublic == null ? true : this.isPublic)
                    .summary(this.summary)
                    .positionType(this.positionType)
                    .selfIntroduction(this.selfIntroduction)
                    .user(user)
                    .build();

            // 기술스택
            if (this.resumeTechStacks != null) {
                for (String tech : this.resumeTechStacks) {
                    ResumeTechStack rts = ResumeTechStack.builder()
                            .techStack(tech)
                            .resume(resume)
                            .build();
                    resume.getResumeTechStacks().add(rts);
                }
            }

            // 학력
            if (this.educations != null) {
                for (EducationDTO dto : this.educations) {
                    Education edu = Education.builder()
                            .graduationDate(dto.getGraduationDate())
                            .isDropout(dto.getIsDropout())
                            .educationLevel(dto.getEducationLevel())
                            .schoolName(dto.getSchoolName())
                            .major(dto.getMajor())
                            .gpa(dto.getGpa())
                            .gpaScale(dto.getGpaScale())
                            .resume(resume)
                            .build();
                    resume.getEducations().add(edu);
                }
            }

            // 프로젝트
            if (this.projects != null) {
                for (ProjectDTO dto : this.projects) {
                    Project project = Project.builder()
                            .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .isOngoing(dto.getIsOngoing())
                            .title(dto.getTitle())
                            .summary(dto.getSummary())
                            .description(dto.getDescription())
                            .repositoryUrl(dto.getRepositoryUrl())
                            .resume(resume)
                            .build();

                    if (dto.getTechStacks() != null) {
                        for (String tech : dto.getTechStacks()) {
                            ProjectTechStack pts = ProjectTechStack.builder()
                                    .techStack(tech)
                                    .project(project)
                                    .build();
                            project.getProjectTechStacks().add(pts);
                        }
                    }
                    resume.getProjects().add(project);
                }
            }

            // 경력
            if (this.experiences != null) {
                for (ExperienceDTO dto : this.experiences) {
                    Experience exp = Experience.builder()
                            .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .isEmployed(dto.getIsEmployed())
                            .companyName(dto.getCompanyName())
                            .summary(dto.getSummary())
                            .position(dto.getPosition())
                            .responsibility(dto.getResponsibility())
                            .resume(resume)
                            .build();

                    if (dto.getTechStacks() != null) {
                        for (String tech : dto.getTechStacks()) {
                            ExperienceTechStack ets = ExperienceTechStack.builder()
                                    .techStack(tech)
                                    .experience(exp)
                                    .build();
                            exp.getExperienceTechStacks().add(ets);
                        }
                    }
                    resume.getExperiences().add(exp);
                }
            }

            // 링크
            if (this.links != null) {
                for (LinkDTO dto : this.links) {
                    Link link = Link.builder()
                            .title(dto.getTitle())
                            .url(dto.getUrl())
                            .resume(resume)
                            .build();
                    resume.getLinks().add(link);
                }
            }

            // 교육이력
            if (this.trainings != null) {
                for (TrainingDTO dto : this.trainings) {
                    Training training = Training.builder()
                            .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .isOngoing(dto.getIsOngoing())
                            .courseName(dto.getCourseName())
                            .institutionName(dto.getInstitutionName())
                            .description(dto.getDescription())
                            .createdAt(dto.getCreatedAt())
                            .resume(resume)
                            .build();

                    if (dto.getTechStacks() != null) {
                        for (String tech : dto.getTechStacks()) {
                            TrainingTechStack tts = TrainingTechStack.builder()
                                    .techStack(tech)
                                    .training(training)
                                    .build();
                            training.getTrainingTechStacks().add(tts);
                        }
                    }
                    resume.getTrainings().add(training);
                }
            }

            // 기타사항
            if (this.etcs != null) {
                for (EtcDTO dto : this.etcs) {
                    Etc etc = Etc.builder()
                            .startDate(dto.getStartDate())
                            .endDate(dto.getEndDate())
                            .hasEndDate(dto.getHasEndDate())
                            .title(dto.getTitle())
                            .etcType(dto.getEtcType())
                            .institutionName(dto.getInstitutionName())
                            .description(dto.getDescription())
                            .resume(resume)
                            .build();
                    resume.getEtcs().add(etc);
                }
            }
            return resume;
        }
    }
}

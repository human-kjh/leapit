package com.example.leapit.resume;

import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.education.EducationResponse;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.etc.EtcResponse;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.experience.ExperienceResponse;
import com.example.leapit.resume.link.LinkResponse;
import com.example.leapit.resume.project.Project;
import com.example.leapit.resume.project.ProjectResponse ;
import com.example.leapit.resume.project.techstack.ProjectTechStack;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.training.Training;
import com.example.leapit.resume.training.TrainingResponse;
import com.example.leapit.resume.training.techstack.TrainingTechStack;
import com.example.leapit.user.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ResumeResponse {

    @Data
    public static class UpdateDTO {
        private Integer id;
        private String title;
        private String photoUrl;
        private String name;
        private String email;
        private Integer birthDate;
        private String contactNumber;
        private Boolean isPublic;
        private String positionType;
        private String summary;
        private String selfIntroduction;

        private List<PositionTypeDTO> positionTypeList;
        private List<TechStack> allTechStacks;
        private List<ResumeTechStackDTO> techStackList;

        private List<LinkDTO> links = new ArrayList<>();
        private List<EducationDTO> educations = new ArrayList<>();
        private List<ExperienceDTO> experiences = new ArrayList<>();
        private List<ProjectDTO> projects = new ArrayList<>();
        private List<TrainingDTO> trainings = new ArrayList<>();
        private List<EtcDTO> etcs = new ArrayList<>();

        @Data
        public static class PositionTypeDTO {
            private String code;
            private Boolean selected;

            public PositionTypeDTO(String code, boolean selected) {
                this.code = code;
                this.selected = selected;
            }
        }

        @Data
        public static class ResumeTechStackDTO {
            private String code;
            private Boolean checked;

            public ResumeTechStackDTO(String code, Boolean checked) {
                this.code = code;
                this.checked = checked;
            }
        }

        @Data
        public static class LinkDTO {
            private Integer id;
            private String title;
            private String url;

            public LinkDTO(Link link) {
                this.id = link.getId();
                this.title = link.getTitle();
                this.url = link.getUrl();
            }
        }

        @Data
        public static class EducationDTO {
            private Integer id;
            private LocalDate graduationDate;
            private Boolean isDropout;
            private String educationLevel;
            private Boolean isHighSchool;
            private Boolean isAssociate;
            private Boolean isBachelor;
            private Boolean isMaster;
            private Boolean isDoctor;
            private String schoolName;
            private String major;
            private String gpa;
            private String gpaScale;
            private Boolean isGpaScale43;
            private Boolean isGpaScale45;
            private String formattedGraduationDate;

            public EducationDTO(Education education) {
                this.id = education.getId();
                this.graduationDate = education.getGraduationDate();
                this.isDropout = education.getIsDropout();
                this.educationLevel = education.getEducationLevel();
                this.isHighSchool = "고등학교".equals(educationLevel);
                this.isAssociate = "전문학사".equals(educationLevel);
                this.isBachelor = "학사".equals(educationLevel);
                this.isMaster = "석사".equals(educationLevel);
                this.isDoctor = "박사".equals(educationLevel);
                this.schoolName = education.getSchoolName();
                this.major = education.getMajor();
                this.gpa = education.getGpa() != null ? education.getGpa().toString() : null;
                this.gpaScale = education.getGpaScale() != null ? education.getGpaScale().toString() : null;
                if (this.gpaScale != null) {
                    this.isGpaScale43 = this.gpaScale.equals("4.3");
                    this.isGpaScale45 = this.gpaScale.equals("4.5");
                } else {
                    this.isGpaScale43 = false;
                    this.isGpaScale45 = false;
                }
                if (this.graduationDate != null) {
                    this.formattedGraduationDate = this.graduationDate.toString().substring(0, 7);
                } else {
                    this.formattedGraduationDate = "";
                }
            }
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
            private List<ExperienceTechStackDTO> techStacks;

            @Data
            public static class ExperienceTechStackDTO {
                private String code;
                private Boolean checked;

                public ExperienceTechStackDTO(String code, Boolean checked) {
                    this.code = code;
                    this.checked = checked;
                }
            }

            public ExperienceDTO(Experience experience, List<TechStack> allTechStacks) {
                this.id = experience.getId();
                this.startDate = experience.getStartDate();
                this.endDate = experience.getEndDate();
                this.isEmployed = experience.getIsEmployed();
                this.companyName = experience.getCompanyName();
                this.summary = experience.getSummary();
                this.position = experience.getPosition();
                this.responsibility = experience.getResponsibility();
                this.techStacks = new ArrayList<>();
                for (TechStack techStack : allTechStacks) {
                    boolean isChecked = false;
                    if (experience.getExperienceTechStacks() != null) {
                        for (ExperienceTechStack ets : experience.getExperienceTechStacks()) {
                            if (techStack.getCode().equals(ets.getTechStack())) {
                                isChecked = true;
                                break;
                            }
                        }
                    }
                    this.techStacks.add(new ExperienceTechStackDTO(techStack.getCode(), isChecked));
                }
            }
        }

        @Data
        public static class ProjectDTO {
            private Integer id;
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isOngoing;
            private String title;
            private String summary;
            private String description;
            private String repositoryUrl;
            private List<ProjectTechStackDTO> techStacks;

            @Data
            public static class ProjectTechStackDTO {
                private String code;
                private Boolean checked;

                public ProjectTechStackDTO(String code, Boolean checked) {
                    this.code = code;
                    this.checked = checked;
                }
            }

            public ProjectDTO(Project project, List<TechStack> allTechStacks) {
                this.id = project.getId();
                this.startDate = project.getStartDate();
                this.endDate = project.getEndDate();
                this.isOngoing = project.getIsOngoing();
                this.title = project.getTitle();
                this.summary = project.getSummary();
                this.description = project.getDescription();
                this.repositoryUrl = project.getRepositoryUrl();
                this.techStacks = new ArrayList<>();
                for (TechStack techStack : allTechStacks) {
                    boolean isChecked = false;
                    if (project.getProjectTechStacks() != null) {
                        for (ProjectTechStack pts : project.getProjectTechStacks()) {
                            if (techStack.getCode().equals(pts.getTechStack())) {
                                isChecked = true;
                                break;
                            }
                        }
                    }
                    this.techStacks.add(new ProjectTechStackDTO(techStack.getCode(), isChecked));
                }
            }
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
            private List<TrainingTechStackDTO> techStacks;

            @Data
            public static class TrainingTechStackDTO {
                private String code;
                private Boolean checked;

                public TrainingTechStackDTO(String code, Boolean checked) {
                    this.code = code;
                    this.checked = checked;
                }
            }

            public TrainingDTO(Training training, List<TechStack> allTechStacks) {
                this.id = training.getId();
                this.startDate = training.getStartDate();
                this.endDate = training.getEndDate();
                this.isOngoing = training.getIsOngoing();
                this.courseName = training.getCourseName();
                this.institutionName = training.getInstitutionName();
                this.description = training.getDescription();
                this.techStacks = new ArrayList<>();
                for (TechStack techStack : allTechStacks) {
                    boolean isChecked = false;
                    if (training.getTrainingTechStacks() != null) {
                        for (TrainingTechStack tts : training.getTrainingTechStacks()) {
                            if (techStack.getCode().equals(tts.getTechStack())) {
                                isChecked = true;
                                break;
                            }
                        }
                    }
                    this.techStacks.add(new TrainingTechStackDTO(techStack.getCode(), isChecked));
                }
            }
        }

        @Data
        public static class EtcDTO {
            private Integer id;
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean hasEndDate;
            private String title;
            private String etcType;
            private Boolean isCertificate;
            private Boolean isLanguage;
            private Boolean isAward;
            private Boolean isEtc;
            private String institutionName;
            private String description;

            public EtcDTO(Etc etc) {
                this.id = etc.getId();
                this.startDate = etc.getStartDate();
                this.endDate = etc.getEndDate();
                this.hasEndDate = etc.getHasEndDate();
                this.title = etc.getTitle();
                this.etcType = etc.getEtcType();
                if ("자격증".equals(etcType)) {
                    this.isCertificate = true;
                } else {
                    this.isCertificate = false;
                }

                if ("어학".equals(etcType)) {
                    this.isLanguage = true;
                } else {
                    this.isLanguage = false;
                }

                if ("수상".equals(etcType)) {
                    this.isAward = true;
                } else {
                    this.isAward = false;
                }

                if ("기타".equals(etcType)) {
                    this.isEtc = true;
                } else {
                    this.isEtc = false;
                }
                this.institutionName = etc.getInstitutionName();
                this.description = etc.getDescription();
            }
        }

        public UpdateDTO(Resume resume, List<PositionType> positionTypes, List<TechStack> techStacks,
                         List<ResumeTechStackDTO> resumeTechStacks, List<LinkDTO> links, List<EducationDTO> educations,
                         List<ExperienceDTO> experiences, List<ProjectDTO> projects,
                         List<TrainingDTO> trainings, List<EtcDTO> etcs) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.photoUrl = resume.getPhotoUrl();
            this.name = resume.getUser().getName();
            this.email = resume.getUser().getEmail();
            this.birthDate = resume.getUser().getBirthDate().getYear();
            this.contactNumber = resume.getUser().getContactNumber();
            this.isPublic = resume.getIsPublic();
            this.positionType = resume.getPositionType();
            this.summary = resume.getSummary();
            this.selfIntroduction = resume.getSelfIntroduction();
            this.allTechStacks = techStacks;
            this.links = links;
            this.educations = educations;
            this.experiences = experiences;
            this.projects = projects;
            this.trainings = trainings;
            this.etcs = etcs;
            this.techStackList = resumeTechStacks;
            this.positionTypeList = new ArrayList<>();
            for (PositionType pt : positionTypes) {
                boolean selected = pt.getCode().equals(resume.getPositionType());
                this.positionTypeList.add(new PositionTypeDTO(pt.getCode(), selected));
            }
        }
    }


    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String photoUrl;
        // 유저 정보
        private String name;
        private String email;
        private Integer birthDate;
        private String contactNumber;

        // 공개 여부
        private Boolean isPublic;

        private String positionType;
        private String summary;
        private String selfIntroduction;

        private List<ResumeTechStack> techStackList = new ArrayList<>();
        private List<LinkResponse.DetailDTO> links = new ArrayList<>();
        private List<EducationResponse.DetailDTO> educations = new ArrayList<>();
        private List<ExperienceResponse.DetailDTO> experiences = new ArrayList<>();
        private List<ProjectResponse.DetailDTO> projects = new ArrayList<>();
        private List<TrainingResponse.DetailDTO> trainings = new ArrayList<>();
        private List<EtcResponse.DetailDTO> etcs = new ArrayList<>();



        public DetailDTO(Resume resume, List<ResumeTechStack> techStacks, List<LinkResponse.DetailDTO> links,
                         List<EducationResponse.DetailDTO> educations, List<ExperienceResponse.DetailDTO> experiences, List<ProjectResponse.DetailDTO> projects,
                         List<TrainingResponse.DetailDTO> trainings, List<EtcResponse.DetailDTO> etcs) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.photoUrl = resume.getPhotoUrl();
            this.name = resume.getUser().getName();
            this.email = resume.getUser().getEmail();
            this.birthDate = resume.getUser().getBirthDate().getYear();
            this.contactNumber = resume.getUser().getContactNumber();
            this.isPublic = resume.getIsPublic();
            this.positionType = resume.getPositionType();
            this.summary = resume.getSummary();
            this.selfIntroduction = resume.getSelfIntroduction();
            this.techStackList = techStacks;
            this.links = links;
            this.educations = educations;
            this.experiences = experiences;
            this.projects = projects;
            this.trainings = trainings;
            this.etcs = etcs;
        }
    }

    @Data
    public static class SaveDTO{
        private String name;
        private String email;
        private Integer birthDate; // 생년월일 중 생년만 받아야 됨
        private String contactNumber;
        private List<PositionType> positionTypeList;
        private List<TechStack> techStackList;

        public SaveDTO(User user, List<PositionType> positionTypeList, List<TechStack> techStackList) {
            this.name = user.getName();
            this.email = user.getEmail();
            this.birthDate = user.getBirthDate().getYear();
            this.contactNumber = user.getContactNumber();
            this.positionTypeList = positionTypeList;
            this.techStackList = techStackList;
        }
    }
}

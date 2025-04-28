package com.example.leapit.resume;

import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.experience.ExperienceResponse;
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
        private List<String> resumeTechStacks;

        private List<LinkDTO> links = new ArrayList<>();
        private List<EducationDTO> educations = new ArrayList<>();
        private List<ExperienceDTO> experiences = new ArrayList<>();
        private List<ProjectDTO> projects = new ArrayList<>();
        private List<TrainingDTO> trainings = new ArrayList<>();
        private List<EtcDTO> etcs = new ArrayList<>();

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
            private String schoolName;
            private String major;
            private String gpa;
            private String gpaScale;
            private String formattedGraduationDate;

            public EducationDTO(Education education) {
                this.id = education.getId();
                this.graduationDate = education.getGraduationDate();
                this.isDropout = education.getIsDropout();
                this.educationLevel = education.getEducationLevel();
                this.schoolName = education.getSchoolName();
                this.major = education.getMajor();
                this.gpa = education.getGpa() != null ? education.getGpa().toString() : null;
                this.gpaScale = education.getGpaScale() != null ? education.getGpaScale().toString() : null;
                // graduationDate = DB에서는 yyyy-mm-01, 화면에서는 yyyy-mm
                if (this.graduationDate != null) {
                    this.formattedGraduationDate = this.graduationDate.toString().substring(0, 7);
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
            private List<String> techStacks;

            public ExperienceDTO(Experience experience) {
                this.id = experience.getId();
                this.startDate = experience.getStartDate();
                this.endDate = experience.getEndDate();
                this.isEmployed = experience.getIsEmployed();
                this.companyName = experience.getCompanyName();
                this.summary = experience.getSummary();
                this.position = experience.getPosition();
                this.responsibility = experience.getResponsibility();
                this.techStacks = new ArrayList<>();
                if (experience.getExperienceTechStacks() != null) {
                    for (ExperienceTechStack ets : experience.getExperienceTechStacks()) {
                        this.techStacks.add(ets.getTechStack());
                    }
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
            private List<String> techStacks;

            public ProjectDTO(Project project) {
                this.id = project.getId();
                this.startDate = project.getStartDate();
                this.endDate = project.getEndDate();
                this.isOngoing = project.getIsOngoing();
                this.title = project.getTitle();
                this.summary = project.getSummary();
                this.description = project.getDescription();
                this.repositoryUrl = project.getRepositoryUrl();
                this.techStacks = new ArrayList<>();
                if (project.getProjectTechStacks() != null) {
                    for (ProjectTechStack pts : project.getProjectTechStacks()) {
                        this.techStacks.add(pts.getTechStack());
                    }
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
            private List<String> techStacks;

            public TrainingDTO(Training training) {
                this.id = training.getId();
                this.startDate = training.getStartDate();
                this.endDate = training.getEndDate();
                this.isOngoing = training.getIsOngoing();
                this.courseName = training.getCourseName();
                this.institutionName = training.getInstitutionName();
                this.description = training.getDescription();
                this.techStacks = new ArrayList<>();
                if (training.getTrainingTechStacks() != null) {
                    for (TrainingTechStack tts : training.getTrainingTechStacks()) {
                        this.techStacks.add(tts.getTechStack());
                    }
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
            private String institutionName;
            private String description;

            public EtcDTO(Etc etc) {
                this.id = etc.getId();
                this.startDate = etc.getStartDate();
                this.endDate = etc.getEndDate();
                this.hasEndDate = etc.getHasEndDate();
                this.title = etc.getTitle();
                this.etcType = etc.getEtcType();
                this.institutionName = etc.getInstitutionName();
                this.description = etc.getDescription();
            }
        }

        // UpdateDTO 생성자
        public UpdateDTO(Resume resume, List<String> resumeTechStacks,
                         List<LinkDTO> links, List<EducationDTO> educations,
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
            this.resumeTechStacks = resumeTechStacks;
            this.links = links;
            this.educations = educations;
            this.experiences = experiences;
            this.projects = projects;
            this.trainings = trainings;
            this.etcs = etcs;
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
        private Integer birthDate; // 생년월일 중 생년만 받아야 됨
        private String contactNumber;

        // 공개 여부
        private Boolean isPublic;

        private String positionType; // code -> label로 전환 필요
        private String summary;
        private String selfIntroduction;

        private List<ResumeTechStack> techStackList;


        private List<Link> links = new ArrayList<>(); // 없을 수도 있으니까 초기화
        private List<Education> educations;
        private List<ExperienceResponse.DetailDTO> experiences = new ArrayList<>();
        private List<ProjectResponse.DetailDTO> projects;
        private List<TrainingResponse.DetailDTO> trainings = new ArrayList<>();
        private List<Etc> etcs = new ArrayList<>();



        public DetailDTO(Resume resume, List<ResumeTechStack> techStacks, List<Link> links,
                         List<Education> educations, List<ExperienceResponse.DetailDTO> experiences, List<ProjectResponse.DetailDTO> projects,
                         List<TrainingResponse.DetailDTO> trainings, List<Etc> etcs) {
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

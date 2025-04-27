package com.example.leapit.resume;

import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.link.LinkResponse;
import com.example.leapit.resume.education.EducationResponse;
import com.example.leapit.resume.experience.ExperienceResponse;
import com.example.leapit.resume.project.ProjectResponse ;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.techstack.ResumeTechStackResponse;
import com.example.leapit.resume.training.TrainingResponse;
import com.example.leapit.resume.etc.EtcResponse;

import com.example.leapit.user.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class ResumeResponse {

    @Data
    public static class DetailDTO {
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

package com.example.leapit.resume;

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
        private String birthDate; // 생년월일 중 생년만 받아야 됨
        private String contactNumber;

        // 공개 여부
        private Boolean isPublic;

        private String positionType; // code -> label로 전환 필요
        private String selfIntroduction;

        private List<ResumeTechStack> techStackList;


        private List<Link> links = new ArrayList<>(); // 없을 수도 있으니까 초기화
        private List<Education> educations;
//        private List<ExperienceResponse.DetailDTO> experiences = new ArrayList<>();
//        private List<ProjectResponse.DetailDTO> projects;
//        private List<TrainingResponse.DetailDTO> trainings = new ArrayList<>();
        private List<Etc> etcs = new ArrayList<>();



        public DetailDTO(Resume resume, String label, List<ResumeTechStack> techStacks, List<Link> links, List<Education> educations, List<Etc> etcs) {
            this.title = resume.getTitle();
            this.photoUrl = resume.getPhotoUrl();
            this.name = resume.getUser().getName();
            this.email = resume.getUser().getEmail();
            this.birthDate = resume.getUser().getEmail();
            this.contactNumber = resume.getUser().getContactNumber();
            this.isPublic = resume.getIsPublic();
            this.positionType = label;
            this.selfIntroduction = resume.getSelfIntroduction();
            this.techStackList = techStacks;
            this.links = links;
            this.educations = educations;
            this.etcs = etcs;
        }
    }
}

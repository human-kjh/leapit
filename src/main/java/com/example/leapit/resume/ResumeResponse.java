package com.example.leapit.resume;

import com.example.leapit.resume.link.LinkResponse;
import com.example.leapit.resume.education.EducationResponse;
import com.example.leapit.resume.experience.ExperienceResponse;
import com.example.leapit.resume.project.ProjectResponse ;
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
        private String email;
        private String birthDate; // 생년월일 중 생년만 받아야 됨
        private String phoneNumber;

        // 공개 여부
        private Boolean isPublic;

        private String positionType;

        private List<ResumeTechStackResponse.DetailDTO> techStacks;

        private List<LinkResponse.DetailDTO> links = new ArrayList<>(); // 없을 수도 있으니까 초기화
        private List<EducationResponse.DetailDTO> educations;
        private List<ExperienceResponse.DetailDTO> experiences = new ArrayList<>();
        private List<ProjectResponse.DetailDTO> projects;
        private List<TrainingResponse.DetailDTO> trainings = new ArrayList<>();
        private List<EtcResponse.DetailDTO> etcs = new ArrayList<>();

        private String selfIntroduction;

    }
}

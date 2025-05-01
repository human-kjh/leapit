package com.example.leapit.resume;


import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.project.Project;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.training.Training;

import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "resume_tb")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String title;

    private String photoUrl;

    @Lob
    private String summary;

    @Column(name = "position_type", nullable = false)
    private String positionType;

    // 기술스택
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeTechStack> resumeTechStacks  = new ArrayList<>(); // 없을 수도 있으니까 초기화
    // 학력
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations  = new ArrayList<>();
    // 프로젝트
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects  = new ArrayList<>();
    // 경력
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences  = new ArrayList<>();
    // 링크
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links  = new ArrayList<>();
    // 교육이력
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings  = new ArrayList<>();
    // 기타사항
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etc> etcs  = new ArrayList<>();

    @Lob
    private String selfIntroduction;

    @Column(nullable = false)
    private Boolean isPublic = true;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Builder
    public Resume(Integer id, User user, String title, String photoUrl, String summary, String positionType,
                  List<ResumeTechStack> resumeTechStacks, List<Education> educations, List<Project> projects,
                  List<Experience> experiences, List<Link> links, List<Training> trainings, List<Etc> etcs,
                  String selfIntroduction, Boolean isPublic, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.photoUrl = photoUrl;
        this.summary = summary;
        this.positionType = positionType;
        this.resumeTechStacks = resumeTechStacks != null ? resumeTechStacks : new ArrayList<>();
        this.educations = educations != null ? educations : new ArrayList<>();
        this.projects = projects != null ? projects : new ArrayList<>();
        this.experiences = experiences != null ? experiences : new ArrayList<>();
        this.links = links != null ? links : new ArrayList<>();
        this.trainings = trainings != null ? trainings : new ArrayList<>();
        this.etcs = etcs != null ? etcs : new ArrayList<>();
        this.selfIntroduction = selfIntroduction;
        this.isPublic = isPublic != null ? isPublic : true;
        this.createdAt = createdAt;
    }

    public void update(String title, String photoUrl, Boolean isPublic, String summary, String positionType, String selfIntroduction) {
        this.title = title;
        this.photoUrl = photoUrl;
        this.isPublic = isPublic != null ? isPublic : true;
        this.summary = summary;
        this.positionType = positionType;
        this.selfIntroduction = selfIntroduction;
    }
}
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
}
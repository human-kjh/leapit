package com.example.leapit.resume.project;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import com.example.leapit.resume.project.techstack.ProjectTechStack;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "project_tb")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isOngoing;

    @Column(nullable = false)
    private String title;

    private String summary;

    @Lob
    private String description;

    private String repositoryUrl;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTechStack> projectTechStacks  = new ArrayList<>();
}

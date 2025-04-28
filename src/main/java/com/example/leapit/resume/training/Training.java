package com.example.leapit.resume.training;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.training.techstack.TrainingTechStack;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "training_tb")
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isOngoing;

    private String courseName;
    private String institutionName;

    @Lob
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingTechStack> trainingTechStacks  = new ArrayList<>();

    @Builder
    public Training(Integer id, Resume resume, LocalDate startDate, LocalDate endDate, Boolean isOngoing, String courseName, String institutionName, String description, Timestamp createdAt, List<TrainingTechStack> trainingTechStacks) {
        this.id = id;
        this.resume = resume;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOngoing = isOngoing;
        this.courseName = courseName;
        this.institutionName = institutionName;
        this.description = description;
        this.createdAt = createdAt;
        this.trainingTechStacks = trainingTechStacks!= null ? trainingTechStacks : new ArrayList<>();
    }

    public void update(LocalDate startDate, LocalDate endDate, Boolean isOngoing, String courseName, String institutionName, String description, Timestamp createdAt) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOngoing = isOngoing;
        this.courseName = courseName;
        this.institutionName = institutionName;
        this.description = description;
        this.createdAt = createdAt;
    }
}
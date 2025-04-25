package com.example.leapit.resume.experience;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
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
@Table(name = "experience_tb")
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isEmployed;

    @Column(nullable = false)
    private String companyName;

    private String summary;
    private String position;

    @Lob
    private String responsibility;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "experience", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceTechStack> experienceTechStacks  = new ArrayList<>();

    @Builder
    public Experience(Integer id, Resume resume, LocalDate startDate, LocalDate endDate, Boolean isEmployed, String companyName, String summary, String position, String responsibility, Timestamp createdAt, List<ExperienceTechStack> experienceTechStacks) {
        this.id = id;
        this.resume = resume;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEmployed = isEmployed;
        this.companyName = companyName;
        this.summary = summary;
        this.position = position;
        this.responsibility = responsibility;
        this.createdAt = createdAt;
        this.experienceTechStacks = experienceTechStacks;
    }
}

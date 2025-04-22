package com.example.leapit.resume.project;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "project_tb")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
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
}

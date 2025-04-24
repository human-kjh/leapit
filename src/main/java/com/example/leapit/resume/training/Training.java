package com.example.leapit.resume.training;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "training_tb")
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
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
}
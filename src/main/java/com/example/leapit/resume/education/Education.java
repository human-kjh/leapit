package com.example.leapit.resume.education;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "education_tb")
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    private LocalDate graduationDate;

    private Boolean isDropout = false;

    @Column(nullable = false)
    private String educationLevel;

    @Column(nullable = false)
    private String schoolName;

    private String major;

    @Column(precision = 3, scale = 2)
    private BigDecimal gpa;

    @Column(precision = 3, scale = 2)
    private BigDecimal gpaScale;

    @CreationTimestamp
    private Timestamp createdAt;
}
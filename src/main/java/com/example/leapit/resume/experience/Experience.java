package com.example.leapit.resume.experience;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "experience_tb")
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
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
}

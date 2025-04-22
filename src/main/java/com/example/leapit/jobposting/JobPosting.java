package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "job_posting_tb")
@Entity
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;
    private String positionType;
    private Integer minCareerLevel;
    private Integer maxCareerLevel;
    private String educationLevel;
    private Integer addressRegionId;
    private Integer addressSubRegionId;
    private String addressDetail;

    @Lob
    private String serviceIntro;

    private LocalDate deadline;

    @Lob
    private String responsibility;

    @Lob
    private String qualification;

    @Lob
    private String preference;

    @Lob
    private String benefit;

    @Lob
    private String additionalInfo;
    private Integer viewCount;

    @CreationTimestamp
    private Timestamp createdAt;
}
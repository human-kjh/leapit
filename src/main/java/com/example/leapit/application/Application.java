package com.example.leapit.application;

import com.example.leapit.application.bookmark.ApplicationBookmark;
import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "application_tb")
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ApplicationBookmark> applicationBookmarks;

    private LocalDate appliedDate;
    private Boolean isPassed;
    private Boolean isViewed = false;

    @Builder
    public Application(Integer id) {
        this.id = id;
    }


    public Application(Resume resume, JobPosting jobPosting, Boolean isPassed, Boolean isViewed, LocalDate appliedDate) {
        this.resume = resume;
        this.jobPosting = jobPosting;
        this.isPassed = isPassed;
        this.isViewed = isViewed;
        this.appliedDate = appliedDate;
    }

    public void update(Boolean isPassed) {
        this.isPassed = isPassed;
    }
}
package com.example.leapit.jobposting.bookmark;

import com.example.leapit.application.Application;
import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "job_posting_bookmark_tb")
@Entity
public class JobPostingBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private JobPosting jobPosting;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public JobPostingBookmark(Integer id, User user, JobPosting jobPosting, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.jobPosting = jobPosting;
        this.createdAt = createdAt;
    }
}

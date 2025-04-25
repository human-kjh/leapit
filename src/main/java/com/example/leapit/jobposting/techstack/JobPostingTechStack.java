package com.example.leapit.jobposting.techstack;

import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.jobposting.JobPosting;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "job_posting_tech_stack_tb")
@Entity
public class JobPostingTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_stack_code", referencedColumnName = "code") // 외래 키 설정
    private TechStack techStack;

    public JobPostingTechStack(JobPosting jobPosting, TechStack techStack) {
        this.jobPosting = jobPosting;
        this.techStack = techStack;
    }
}
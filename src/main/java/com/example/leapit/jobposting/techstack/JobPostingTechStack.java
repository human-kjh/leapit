package com.example.leapit.jobposting.techstack;
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
    private JobPosting jobPosting;

    @Column(nullable = false)
    private String techStack;
}

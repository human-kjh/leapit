package com.example.leapit.resume.techstack;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "resume_tech_stack_tb")
@Entity
public class ResumeTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false)
    private String techStack;

    @Builder
    public ResumeTechStack(Integer id, Resume resume, String techStack) {
        this.id = id;
        this.resume = resume;
        this.techStack = techStack;
    }
}

package com.example.leapit.resume.training.techstack;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.training.Training;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "training_tech_stack_tb")
@Entity
public class TrainingTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private String techStack;
}

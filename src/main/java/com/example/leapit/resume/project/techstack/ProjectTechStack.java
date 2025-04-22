package com.example.leapit.resume.project.techstack;
import com.example.leapit.resume.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "project_tech_stack_tb")
@Entity
public class ProjectTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Column(nullable = false)
    private String techStack;
}

package com.example.leapit.resume.project.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProjectTechStackRepository {
    private final EntityManager em;
}

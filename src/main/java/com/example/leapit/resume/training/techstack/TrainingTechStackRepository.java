package com.example.leapit.resume.training.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TrainingTechStackRepository {
    private final EntityManager em;
}

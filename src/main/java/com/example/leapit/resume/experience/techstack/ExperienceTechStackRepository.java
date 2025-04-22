package com.example.leapit.resume.experience.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ExperienceTechStackRepository {
    private final EntityManager em;
}

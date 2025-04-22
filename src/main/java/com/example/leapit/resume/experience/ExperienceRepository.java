package com.example.leapit.resume.experience;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ExperienceRepository {
    private final EntityManager em;
}

package com.example.leapit.resume.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ResumeTechStackRepository {
    private final EntityManager em;
}

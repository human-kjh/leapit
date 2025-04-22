package com.example.leapit.jobposting.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JobPostingTechStackRepository {
    private final EntityManager em;
}

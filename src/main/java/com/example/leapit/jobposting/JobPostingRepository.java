package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    // 등록
    public void save(JobPosting jobPosting) {
        em.persist(jobPosting);
    }
}
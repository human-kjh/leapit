package com.example.leapit.jobposting.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JobPostingTechStackRepository {
    private final EntityManager em;

    public void deleteByJobPostingId(Integer jobPostingId) {
        em.createQuery("DELETE FROM JobPostingTechStack jpts WHERE jpts.jobPosting.id = :jobPostingId")
                .setParameter("jobPostingId", jobPostingId)
                .executeUpdate();
    }
}

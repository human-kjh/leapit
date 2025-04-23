package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

    // 삭제
    public void deleteById(Integer id) {
        JobPosting jobPosting = em.find(JobPosting.class, id);
        if (jobPosting != null) {
            em.remove(jobPosting);
        }
    }

    public JobPosting findById(Integer id) {
        return em.find(JobPosting.class, id);
    }

    @Transactional
    public void update(JobPosting jobPosting) {
        em.merge(jobPosting);
    }
}
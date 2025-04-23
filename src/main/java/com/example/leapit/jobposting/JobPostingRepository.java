package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    // 진행 중인 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineGreaterThanEqual(LocalDate deadline) {
        return em.createQuery("select jp from JobPosting jp where jp.deadline >= :deadline", JobPosting.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }

    // 마감된 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineBefore(LocalDate deadline) {
        return em.createQuery("select jp from JobPosting jp where jp.deadline < :deadline", JobPosting.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }
}
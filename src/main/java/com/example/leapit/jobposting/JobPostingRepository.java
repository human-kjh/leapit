package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
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
        em.remove(jobPosting);
    }

    // 단건 조회
    public JobPosting findById(Integer id) {
        return em.find(JobPosting.class, id);
    }

    // 전체 조회
    public List<JobPosting> findAll() {
        String sql = "SELECT j FROM JobPosting j ORDER BY j.id DESC";
        return em.createQuery(sql, JobPosting.class).getResultList();
    }

    // 진행 중인 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineOpen(LocalDate deadline) {
        return em.createQuery("select jp from JobPosting jp where jp.deadline >= :deadline", JobPosting.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }

    // 마감된 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineClosed(LocalDate deadline) {
        return em.createQuery("select jp from JobPosting jp where jp.deadline < :deadline", JobPosting.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }
}
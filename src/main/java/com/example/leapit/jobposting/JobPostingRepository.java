package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    // 채용공고 & 해당 채용공고의 기술스택 조회
    public List<Object[]> findJobPostingsWithTechStacksByUserId(Integer userId) {
        Query query = em.createQuery(
                "SELECT j, t FROM JobPosting j " +
                        "LEFT JOIN JobPostingTechStack t ON t.jobPosting.id = j.id " +
                        "WHERE j.user.id = :userId"
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // 채용중인 포지션 카운트 조회 (마감일이 오늘 이후인)
    public Long countByUserIdAndDeadlineAfter(Integer userId) {
        Query query = em.createQuery("SELECT COUNT(j) FROM JobPosting j WHERE j.user.id = :userId AND j.deadline >= CURRENT_DATE");
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult();
    }

}
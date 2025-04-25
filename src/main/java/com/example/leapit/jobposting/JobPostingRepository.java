package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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

    // 주소 - 시 조회
    public String findByRegion(Integer id) {
        Query query = em.createNativeQuery("select R.name from job_posting_tb J inner join  region_tb R  on R.id = J.address_region_id where J.id = ?");
        query.setParameter(1, id);

        return (String) query.getSingleResult();
    }

    // 주소 - 구 조회
    public String findBySubRegion(Integer id) {
        Query query = em.createNativeQuery("select S.name from job_posting_tb J inner join  sub_region_tb S  on S.id = J.address_sub_region_id where J.id = ?");
        query.setParameter(1, id);
        return (String) query.getSingleResult();

    }

    // 채용공고 & 해당 채용공고의 기술스택 전체조회
    public List<Object[]> findAllJobPostingsWithTechStacks() {
        LocalDate today = LocalDate.now();

        Query query = em.createQuery(
                "SELECT jp, jpts FROM JobPosting jp " +
                        "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id where jp.deadline >= :today"
        );
        query.setParameter("today", today);
        return query.getResultList();
    }

    // 구직자 메인페이지 - 인기공고 8개
    public List<Object[]> findTop8PopularJobPostingsWithTechStacks() {
        LocalDate today = LocalDate.now();

        // 1. 마감일 이후 + viewCount 기준 상위 8개 채용공고 ID 조회
        List<Integer> top8Ids = em.createQuery(
                        "SELECT jp.id FROM JobPosting jp " +
                                "WHERE jp.deadline >= :today " +
                                "ORDER BY jp.viewCount DESC", Integer.class)
                .setParameter("today", today)
                .setMaxResults(8)
                .getResultList();

        if (top8Ids.isEmpty()) return new ArrayList<>();

        // 2. 해당 ID들 기준으로 기술스택 조인 포함 재조회
        return em.createQuery(
                        "SELECT jp, jpts FROM JobPosting jp " +
                                "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id " +
                                "WHERE jp.id IN :ids", Object[].class)
                .setParameter("ids", top8Ids)
                .getResultList();
    }

    // 구직자 메인페이지 - 최신공고 3개
    public List<JobPosting> findTop3RecentJobPostings() {
        LocalDate today = LocalDate.now();

        Query query = em.createQuery(
                "SELECT jp FROM JobPosting jp " +
                        "WHERE jp.deadline >= :today " +
                        "ORDER BY jp.createdAt DESC"
        );
        query.setParameter("today", today);
        query.setMaxResults(3);

        return query.getResultList();
    }
}
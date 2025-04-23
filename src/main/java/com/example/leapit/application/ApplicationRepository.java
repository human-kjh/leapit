package com.example.leapit.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApplicationRepository {

    private final EntityManager em;

    // 지원 현황 통계
    public ApplicationResponse.ApplicationStatusDto findSummaryByUserId(Integer userId) {
        String jpql = """
        SELECT COUNT(a), 
               SUM(CASE WHEN a.isPassed = true THEN 1 ELSE 0 END), 
               SUM(CASE WHEN a.isPassed = false THEN 1 ELSE 0 END)
        FROM Application a
        JOIN a.resume r
        JOIN r.user u
        WHERE u.id = :userId
    """;

        Object[] result = (Object[]) em.createQuery(jpql)
                .setParameter("userId", userId)
                .getSingleResult();

        return new ApplicationResponse.ApplicationStatusDto(
                (Long) result[0],
                (Long) result[1],
                (Long) result[2]
        );
    }

    // 지원 현황 목록
    public List<ApplicationResponse.ApplicationDto> findApplicationsByUserId(Integer userId) {
        String jpql = """
        SELECT company.username, jp.title, a.appliedDate, r.id, jp.id
        FROM Application a
        JOIN a.resume r
        JOIN r.user u
        JOIN a.jobPosting jp
        JOIN jp.user company
        WHERE u.id = :userId
        ORDER BY a.appliedDate DESC
    """;

        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .setParameter("userId", userId)
                .getResultList();

        return resultList.stream()
                .map(row -> new ApplicationResponse.ApplicationDto(
                        (String) row[0],
                        (String) row[1],
                        (LocalDate) row[2],
                        (Integer) row[3],
                        (Integer) row[4]
                ))
                .toList();
    }
}

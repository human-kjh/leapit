package com.example.leapit.application;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApplicationRepository {

    private final EntityManager em;

    public List<ApplicationResponse.CompanyeApplicantDto> findAllApplicantsByCompanyUserId(Integer companyUserId, Integer jobPostingId,String passStatus) {
        String jpql = """
        SELECT new com.example.leapit.application.ApplicationResponse$CompanyeApplicantDto(
            a.id,
            r.id,
            u.name,
            jpt.title,
            a.appliedDate,
            CASE WHEN abt.id IS NOT NULL THEN true ELSE false END,
            CASE 
                WHEN a.isViewed = false THEN '미열람'
                WHEN a.isViewed = true AND a.isPassed IS NULL THEN '열람'
                WHEN a.isPassed = true THEN '합격'
                WHEN a.isPassed = false THEN '불합격'
            END
        )
        FROM Application a
        JOIN a.resume r
        JOIN r.user u
        JOIN a.jobPosting jpt
        JOIN jpt.user company
        LEFT JOIN ApplicationBookmark abt ON abt.application.id = a.id AND abt.user.id = :companyUserId
        WHERE company.id = :companyUserId
          AND (:jobPostingId IS NULL OR jpt.id = :jobPostingId)
    """;

        if ("합격".equals(passStatus)) {
            jpql += " AND a.isPassed = true";
        } else if ("불합격".equals(passStatus)) {
            jpql += " AND a.isPassed = false";
        }

        jpql += " ORDER BY a.appliedDate DESC";

        return em.createQuery(jpql, ApplicationResponse.CompanyeApplicantDto.class)
                .setParameter("companyUserId", companyUserId)
                .setParameter("jobPostingId", jobPostingId)
                .getResultList();
    }



    public List<ApplicationResponse.IsClosedDTO> positionAndIsClosedDtoBycompanyUserIds(Integer companyUserId) {
        String jpql = """
        SELECT new com.example.leapit.application.ApplicationResponse$IsClosedDTO(
            jpt.id,
            jpt.title,
            CASE WHEN jpt.deadline < CURRENT_DATE THEN true ELSE false END
        )
        FROM JobPosting jpt
        JOIN jpt.user u
        WHERE u.id = :companyUserId
        ORDER BY jpt.createdAt DESC
    """;

        return em.createQuery(jpql, ApplicationResponse.IsClosedDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }
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

    public Application findByApplicationId(Integer applicationId) {
        return em.find(Application.class, applicationId);
    }
}

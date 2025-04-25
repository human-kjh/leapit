package com.example.leapit.application.bookmark;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApplicationBookmarkRepository {
    private final EntityManager em;

    public List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> findAllJobPostingBookmarkByuserId(Integer userId) {
        String jpql = """
        SELECT jp.id, jp.title, jp.deadline, ci.companyName
        FROM JobPostingBookmark jb
        JOIN jb.jobPosting jp
        JOIN jp.user u
        JOIN CompanyInfo ci ON ci.user = u
        WHERE jb.user.id = :userId
        ORDER BY jp.deadline DESC
    """;

        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .setParameter("userId", userId)
                .getResultList();

        List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> dtos = new ArrayList<>();

        for (Object[] row : resultList) {
            Integer jobPostingId = (Integer) row[0];
            String title = (String) row[1];
            LocalDate deadline = (LocalDate) row[2];
            String companyName = (String) row[3];

            dtos.add(new ApplicationBookmarkResponse.JobPostingBookmarkDTO(
                    jobPostingId, companyName, title, deadline
            ));
        }

        return dtos;
    }

    public ApplicationBookmark findByUserIdAndApplicationId(Integer userId, Integer applicationId) {
        try {
            return em.createQuery("""
                    SELECT ab FROM ApplicationBookmark ab
                    WHERE ab.user.id = :userId AND ab.application.id = :applicationId
                """, ApplicationBookmark.class)
                    .setParameter("userId", userId)
                    .setParameter("applicationId", applicationId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void delete(ApplicationBookmark bookmark) {
        em.remove(bookmark);
    }

    public ApplicationBookmark save(ApplicationBookmark bookmark) {
        em.persist(bookmark);
        return bookmark;
    }




}

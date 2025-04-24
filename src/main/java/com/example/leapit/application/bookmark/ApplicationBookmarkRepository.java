package com.example.leapit.application.bookmark;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ApplicationBookmarkRepository {
    private final EntityManager em;

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

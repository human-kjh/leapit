package com.example.leapit.resume;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResumeRepository {
    private final EntityManager em;

    public List<Resume> findAllById(int userId) {
        Query query = em.createQuery("SELECT r FROM Resume r WHERE r.user.id = :userId", Resume.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}

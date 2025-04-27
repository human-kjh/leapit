package com.example.leapit.resume.experience;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ExperienceRepository {
    private final EntityManager em;

    public List<Experience> findAllByResumeId(int resumeId) {
        Query query = em.createQuery("SELECT e FROM Experience e WHERE e.resume.id = :resumeId", Experience.class);
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

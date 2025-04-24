package com.example.leapit.resume.education;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class EducationRepository {
    private final EntityManager em;

    public List<Education> findAllByResumeId(int resumeId) {
        Query query = em.createQuery("SELECT e FROM Education e WHERE e.resume.id = :resumeId");
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

package com.example.leapit.resume.techstack;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResumeTechStackRepository {
    private final EntityManager em;

    public List<ResumeTechStack> findAllById(Integer resumeId) {
        Query query = em.createQuery("SELECT r FROM ResumeTechStack r WHERE r.resume.id = :resumeId");
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

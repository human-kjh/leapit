package com.example.leapit.resume.training;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TrainingRepository {
    private final EntityManager em;

    public List<Training> findAllByResumeId(Integer resumeId) {
        Query query = em.createQuery("SELECT t FROM Training t WHERE t.resume.id = :resumeId",Training.class);
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

package com.example.leapit.resume.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProjectRepository {
    private final EntityManager em;

    public List<Project> findAllByResumeId(Integer resumeId) {
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.resume.id = :resumeId", Project.class);
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

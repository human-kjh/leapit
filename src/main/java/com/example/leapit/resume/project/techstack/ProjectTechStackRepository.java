package com.example.leapit.resume.project.techstack;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProjectTechStackRepository {
    private final EntityManager em;

    public List<ProjectTechStack> findAllByProjectId(Integer id) {
        Query query = em.createQuery("SELECT p FROM ProjectTechStack p WHERE p.project.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
}

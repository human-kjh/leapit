package com.example.leapit.resume.experience.techstack;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ExperienceTechStackRepository {
    private final EntityManager em;

    public List<ExperienceTechStack> findAllByExperienceId(Integer id) {
        Query query = em.createQuery("SELECT e FROM ExperienceTechStack e WHERE e.experience.id = :id",ExperienceTechStack.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}

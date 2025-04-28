package com.example.leapit.resume.training.techstack;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TrainingTechStackRepository {
    private final EntityManager em;

    public List<TrainingTechStack> findAllByTrainingId(Integer id) {
        Query query = em.createQuery("SELECT t FROM TrainingTechStack t WHERE t.training.id = :id", TrainingTechStack.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}

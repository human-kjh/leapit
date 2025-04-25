package com.example.leapit.common.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TechStackRepository {
    private final EntityManager em;

    public List<TechStack> findAll() {
        return em.createQuery("SELECT ts FROM TechStack ts ORDER BY ts.code", TechStack.class)
                .getResultList();
    }

    public TechStack findByCode(String code) {
        return em.createQuery("SELECT ts FROM TechStack ts WHERE ts.code = :code", TechStack.class)
                .setParameter("code", code)
                .getSingleResult();
    }
}

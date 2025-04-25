package com.example.leapit.common.techstack;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class TechStackRepository {
    private final EntityManager em;

    public List<TechStack> findAll(){
        Query query = em.createQuery("select t from TechStack t");
        return query.getResultList();
    }
}

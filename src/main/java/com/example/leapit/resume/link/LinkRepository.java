package com.example.leapit.resume.link;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class LinkRepository {
    private final EntityManager em;

    public List<Link> findAllByResumeId(Integer resumeId) {
        Query query = em.createQuery("select l from Link l where l.resume.id = :resumeId");
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

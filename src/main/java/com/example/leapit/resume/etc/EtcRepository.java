package com.example.leapit.resume.etc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class EtcRepository {
    private final EntityManager em;

    public List<Etc> findAllById(int resumeId) {
        Query query = em.createQuery("SELECT e FROM Etc e WHERE e.resume.id = :resumeId");
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }
}

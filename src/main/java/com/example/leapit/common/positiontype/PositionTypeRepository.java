package com.example.leapit.common.positiontype;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PositionTypeRepository {
    private final EntityManager em;

    public PositionType findByCode(String code) {
        return em.find(PositionType.class, code);
    }

    public List<PositionType> findAll() {
        Query query = em.createQuery("SELECT pt FROM PositionType pt", PositionType.class);
        return query.getResultList();
    }
}

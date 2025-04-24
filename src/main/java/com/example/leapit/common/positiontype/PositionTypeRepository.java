package com.example.leapit.common.positiontype;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PositionTypeRepository {
    private final EntityManager em;

    public PositionType findByCode(String code) {
        return em.find(PositionType.class, code);
    }
}

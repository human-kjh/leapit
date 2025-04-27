package com.example.leapit.common.positiontype;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PositionTypeRepository {
    private final EntityManager em;

    public PositionType findByCode(String code) {
        return em.find(PositionType.class, code);
    }

    public List<PositionTypeResponse.PositionTypeDTO> findAllLabel() {
        String jpql = "SELECT p.label FROM PositionType p";
        List<String> labels = em.createQuery(jpql, String.class)
                .getResultList();

        List<PositionTypeResponse.PositionTypeDTO> dtos = new ArrayList<>();
        for (String label : labels) {
            dtos.add(new PositionTypeResponse.PositionTypeDTO(label));
        }
        return dtos;
    }
}

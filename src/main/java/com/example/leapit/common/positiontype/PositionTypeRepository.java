package com.example.leapit.common.positiontype;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    public List<PositionTypeResponse.PositionTypeDTO> findAllLabelAndSelectedLabel(String selectedLabel) {
        String jpql = "SELECT p.code FROM PositionType p";
        List<String> codes = em.createQuery(jpql, String.class)
                .getResultList();

        List<PositionTypeResponse.PositionTypeDTO> dtos = new ArrayList<>();
        for (String code : codes) {
            boolean isSelected = code.equals(selectedLabel);
            dtos.add(new PositionTypeResponse.PositionTypeDTO(code, isSelected));
        }
        return dtos;
    }

    public List<PositionType> findAll() {
        Query query = em.createQuery("SELECT pt FROM PositionType pt", PositionType.class);
        return query.getResultList();
    }
}

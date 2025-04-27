package com.example.leapit.common.region;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class RegionRepository {
    private final EntityManager em;

    public List<RegionResponse.RegionDTO> findAllRegions() {
        List<String> tech = em.createQuery("select r.name from Region r", String.class).getResultList();

        List<RegionResponse.RegionDTO> dtos = new ArrayList<>();
        for (String r : tech) {
            dtos.add(new RegionResponse.RegionDTO(r));
        }
        return dtos;
    }

    public List<RegionResponse.SubRegionDTO> findAllSubRegions(Integer regionId) {
        List<String> tech = em.createQuery("SELECT s.name FROM SubRegion s WHERE s.region.id = :regionId", String.class).setParameter("regionId", regionId).getResultList();

        List<RegionResponse.SubRegionDTO> dtos = new ArrayList<>();
        for (String r : tech) {
            dtos.add(new RegionResponse.SubRegionDTO(r));
        }
        return dtos;
    }
}

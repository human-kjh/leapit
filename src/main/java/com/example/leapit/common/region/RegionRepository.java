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
        List<Region> regions = em.createQuery("SELECT r FROM Region r", Region.class).getResultList();

        List<RegionResponse.RegionDTO> dtos = new ArrayList<>();
        for (Region r : regions) {
            dtos.add(new RegionResponse.RegionDTO(r.getId(), r.getName()));
        }
        return dtos;
    }

    public List<RegionResponse.SubRegionDTO> findAllSubRegions(Integer regionId) {
        List<Object[]> results = em.createQuery(
                        "SELECT s.id, s.name FROM SubRegion s WHERE s.region.id = :regionId", Object[].class)
                .setParameter("regionId", regionId)
                .getResultList();

        List<RegionResponse.SubRegionDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            Integer subRegionId = (Integer) row[0];
            String subRegion = (String) row[1];
            dtos.add(new RegionResponse.SubRegionDTO(subRegionId, subRegion));
        }
        return dtos;
    }
}

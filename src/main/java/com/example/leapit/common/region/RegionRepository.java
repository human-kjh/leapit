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

    // 시/도
    public List<RegionResponse.SelectedRegionDTO> findAllRegionsBySelection(Integer selectedRegionId) {
        List<Region> regions = em.createQuery("SELECT r FROM Region r", Region.class).getResultList();

        List<RegionResponse.SelectedRegionDTO> dtos = new ArrayList<>();
        for (Region r : regions) {
            boolean selected = r.getId().equals(selectedRegionId);
            dtos.add(new RegionResponse.SelectedRegionDTO(r.getId(), r.getName(), selected));
        }
        return dtos;
    }

    // 시/군/구
    public List<RegionResponse.SelectedSubRegionDTO> findAllSubRegionsBySelection(Integer regionId, Integer selectedSubRegionId) {
        List<Object[]> results = em.createQuery(
                        "SELECT s.id, s.name FROM SubRegion s WHERE s.region.id = :regionId", Object[].class)
                .setParameter("regionId", regionId)
                .getResultList();

        List<RegionResponse.SelectedSubRegionDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            Integer subRegionId = (Integer) row[0];
            String name = (String) row[1];
            boolean selected = subRegionId.equals(selectedSubRegionId);
            dtos.add(new RegionResponse.SelectedSubRegionDTO(subRegionId, name, selected));
        }
        return dtos;
    }
    
    public RegionResponse.SelectedRegionSubRegionDTO findSelectedRegionSubRegion(Integer regionId, Integer subRegionId) {
        String sql = "SELECT " +
                "r.region_id, " +
                "r.region, " +
                "sr.sub_region_id, " +
                "sr.sub_region " +
                "FROM region r " +
                "JOIN sub_region sr ON r.region_id = sr.region_id " +
                "WHERE r.region_id = ? AND sr.sub_region_id = ?";

        Object[] result = (Object[]) em.createNativeQuery(sql)
                .setParameter(1, regionId)
                .setParameter(2, subRegionId)
                .getSingleResult();

        return new RegionResponse.SelectedRegionSubRegionDTO(
                ((Number) result[0]).intValue(),   // region_id
                (String) result[1],                // region
                ((Number) result[2]).intValue(),   // sub_region_id
                (String) result[3],                // sub_region
                true
        );
    }
}

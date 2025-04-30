package com.example.leapit.common.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {
    private final RegionRepository regionRepository;

    public List<RegionResponse.SelectedRegionDTO> getRegionsWithSelection(Integer selectedRegionId) {
        List<RegionResponse.RegionDTO> regions = regionRepository.findAllRegions();
        List<RegionResponse.SelectedRegionDTO> result = new ArrayList<>();

        for (RegionResponse.RegionDTO region : regions) {
            boolean isSelected = region.getRegionId().equals(selectedRegionId);
            result.add(new RegionResponse.SelectedRegionDTO(region.getRegionId(), region.getRegion(), isSelected));
        }

        return result;
    }

    public List<RegionResponse.SelectedSubRegionDTO> getSubRegionsWithSelection(Integer regionId, Integer selectedSubRegionId) {
        List<RegionResponse.SubRegionDTO> subRegions = regionRepository.findAllSubRegions(regionId);
        List<RegionResponse.SelectedSubRegionDTO> result = new ArrayList<>();

        for (RegionResponse.SubRegionDTO sub : subRegions) {
            boolean isSelected = sub.getSubRegionId().equals(selectedSubRegionId);
            result.add(new RegionResponse.SelectedSubRegionDTO(sub.getSubRegionId(), sub.getSubRegion(), isSelected));
        }

        return result;
    }

    public List<RegionResponse.SubRegionDTO> getSubRegions(Integer regionId) {
        return regionRepository.findAllSubRegions(regionId);
    }

}

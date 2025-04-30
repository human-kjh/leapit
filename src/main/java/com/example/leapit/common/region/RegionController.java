package com.example.leapit.common.region;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/api/region/subregions")
    public ResponseEntity<List<RegionResponse.SubRegionDTO>> getSubRegions(@RequestParam Integer regionId) {
        List<RegionResponse.SubRegionDTO> subRegions = regionService.getSubRegions(regionId);
        return ResponseEntity.ok(subRegions);
    }
}

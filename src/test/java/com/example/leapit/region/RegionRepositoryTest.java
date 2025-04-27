package com.example.leapit.region;


import com.example.leapit.common.region.RegionRepository;
import com.example.leapit.common.region.RegionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(RegionRepository.class)
@DataJpaTest
public class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    @Test
    public void findAllRegions_test() {
        // when
        List<RegionResponse.RegionDTO> results = regionRepository.findAllRegions();

        // eye
        for (RegionResponse.RegionDTO r : results) {
            System.out.println(r);
        }
    }

    @Test
    public void findAllSubRegions_test() {
        // given
        Integer regionId = 1;

        // when
        List<RegionResponse.SubRegionDTO> results = regionRepository.findAllSubRegions(regionId);

        // eye
        for (RegionResponse.SubRegionDTO r : results) {
            System.out.println(r);
        }
    }

}

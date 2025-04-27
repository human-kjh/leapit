package com.example.leapit.Region;


import com.example.leapit.common.region.RegionRepository;
import com.example.leapit.common.region.RegionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(RegionRepository.class)
@DataJpaTest
public class RegionRepositoryTest {

    @Autowired
    private RegionRepository regionRepository;

    public void findAllRegions() {
        List<RegionResponse.RegionDTO> results = regionRepository.findAllRegions();

        for (RegionResponse.RegionDTO r : results) {
            System.out.println(r);
        }
    }

}

package com.example.leapit.common.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService {
    private final RegionRepository regionRepository;
}

package com.example.leapit.common.region;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegionRepository {
    private final EntityManager em;
}

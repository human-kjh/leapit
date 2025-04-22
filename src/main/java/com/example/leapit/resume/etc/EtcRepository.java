package com.example.leapit.resume.etc;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EtcRepository {
    private final EntityManager em;
}

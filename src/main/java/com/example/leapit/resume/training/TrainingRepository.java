package com.example.leapit.resume.training;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TrainingRepository {
    private final EntityManager em;
}

package com.example.leapit.resume.education;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EducationRepository {
    private final EntityManager em;
}

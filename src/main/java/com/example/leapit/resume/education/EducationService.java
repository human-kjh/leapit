package com.example.leapit.resume.education;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EducationService {
    private final EducationRepository educationRepository;
}

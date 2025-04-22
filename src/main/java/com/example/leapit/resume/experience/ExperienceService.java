package com.example.leapit.resume.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
}

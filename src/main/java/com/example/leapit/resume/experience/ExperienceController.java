package com.example.leapit.resume.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ExperienceController {
    private final ExperienceService experienceService;
}

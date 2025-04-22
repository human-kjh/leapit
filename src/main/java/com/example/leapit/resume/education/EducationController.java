package com.example.leapit.resume.education;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class EducationController {
    private final EducationService educationService;
}

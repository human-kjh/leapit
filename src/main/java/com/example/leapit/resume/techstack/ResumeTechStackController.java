package com.example.leapit.resume.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ResumeTechStackController {
    private final ResumeTechStackService resumeTechStackService;
}

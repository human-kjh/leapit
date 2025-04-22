package com.example.leapit.jobposting.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class JobPostingTechStackController {
    private final JobPostingTechStackService jobPostingTechStackService;
}

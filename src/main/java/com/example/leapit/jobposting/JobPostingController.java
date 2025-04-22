package com.example.leapit.jobposting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class JobPostingController {
    private final JobPostingService jobPostingService;
}

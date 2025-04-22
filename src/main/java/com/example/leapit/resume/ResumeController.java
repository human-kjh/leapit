package com.example.leapit.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ResumeController {
    private final ResumeService resumeService;
}

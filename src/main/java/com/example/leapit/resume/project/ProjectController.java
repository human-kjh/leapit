package com.example.leapit.resume.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ProjectController {
    private final ProjectService projectService;
}

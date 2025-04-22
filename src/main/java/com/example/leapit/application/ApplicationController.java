package com.example.leapit.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ApplicationController {
    private final ApplicationService applicationService;
}

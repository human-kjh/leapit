package com.example.leapit.common.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class TechStackController {
    private final TechStackService techStackService;
}

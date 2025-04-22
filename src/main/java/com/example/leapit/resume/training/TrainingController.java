package com.example.leapit.resume.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class TrainingController {
    private final TrainingService trainingService;
}

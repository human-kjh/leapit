package com.example.leapit.resume.training;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
}

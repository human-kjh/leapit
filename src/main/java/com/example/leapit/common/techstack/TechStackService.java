package com.example.leapit.common.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TechStackService {
    private final TechStackRepository techStackRepository;
}

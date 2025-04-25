package com.example.leapit.common.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechStackService {

    private final TechStackRepository techStackRepository;

    public List<TechStack> getAllTechStacks() {
        return techStackRepository.findAll();
    }
}
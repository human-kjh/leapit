package com.example.leapit.resume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public List<Resume> list(int userId) {
        // 자신의 userId로 된 모든 resume을 찾아서 return
        return resumeRepository.findAllById(userId);
    }
}

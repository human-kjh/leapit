package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public void createJobPosting(JobPostingRequest request, User user) {
        JobPosting jobPosting = request.toEntity(user); // 빌더로 생성
        jobPostingRepository.save(jobPosting);
    }

}
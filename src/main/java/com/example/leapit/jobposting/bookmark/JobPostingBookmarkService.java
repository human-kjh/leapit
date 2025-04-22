package com.example.leapit.jobposting.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostingBookmarkService {
    private final JobPostingBookmarkRepository jobPostingBookmarkRepository;
}

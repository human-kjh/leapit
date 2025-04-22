package com.example.leapit.application.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplicationBookmarkService {
    private final ApplicationBookmarkRepository applicationBookmarkRepository;
}

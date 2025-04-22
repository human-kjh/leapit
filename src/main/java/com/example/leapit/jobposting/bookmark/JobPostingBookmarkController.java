package com.example.leapit.jobposting.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class JobPostingBookmarkController {
    private final JobPostingBookmarkService jobPostingBookmarkService;
}

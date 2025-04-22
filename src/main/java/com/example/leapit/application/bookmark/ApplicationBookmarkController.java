package com.example.leapit.application.bookmark;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ApplicationBookmarkController {
    private final ApplicationBookmarkService applicationBookmarkService;
}

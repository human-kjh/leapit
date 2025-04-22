package com.example.leapit.resume.link;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class LinkController {
    private final LinkService linkService;
}

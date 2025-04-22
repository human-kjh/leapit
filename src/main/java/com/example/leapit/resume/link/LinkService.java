package com.example.leapit.resume.link;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LinkService {
    private final LinkRepository linkRepository;
}

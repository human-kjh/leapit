package com.example.leapit.common.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class RegionController {
    private final RegionService regionService;
}

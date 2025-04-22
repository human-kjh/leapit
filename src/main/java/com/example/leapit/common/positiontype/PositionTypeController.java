package com.example.leapit.common.positiontype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class PositionTypeController {
    private final PositionTypeService positionTypeService;
}

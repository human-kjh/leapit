package com.example.leapit.common.positiontype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PositionTypeService {
    private final PositionTypeRepository positionTypeRepository;
}

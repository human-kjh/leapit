package com.example.leapit.common.positiontype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PositionTypeService {
    private final PositionTypeRepository positionTypeRepository;

    public List<PositionType> list() {
        return positionTypeRepository.findAll();
    }
}

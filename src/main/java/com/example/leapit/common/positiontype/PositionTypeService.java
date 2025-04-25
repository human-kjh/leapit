package com.example.leapit.common.positiontype;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PositionTypeService {
    private final PositionTypeRepository positionTypeRepository;

    // 직무 코드 -> 라벨 (e.g. "backend" -> "서버/백엔드 개발자")
    public String codeToLabel(String code) {
        PositionType positionType = positionTypeRepository.findByCode(code);
        return positionType.getLabel();
    }

}

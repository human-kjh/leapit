package com.example.leapit.common.positiontype;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "position_type_tb")
@Entity
public class PositionType {

    @Id
    private String code; // 예: backend

    @Column(nullable = false)
    private String label; // 예: 서버/백엔드 개발자
}
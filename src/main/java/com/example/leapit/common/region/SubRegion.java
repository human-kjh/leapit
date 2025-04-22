package com.example.leapit.common.region;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "sub_region_tb")
@Entity
public class SubRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;  // 시/군/구명 (예: 부산진구)

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;
}

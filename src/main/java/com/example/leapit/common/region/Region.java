package com.example.leapit.common.region;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "region_tb")
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;  // 시/도명 (예: 부산광역시)

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<SubRegion> subRegions;
}

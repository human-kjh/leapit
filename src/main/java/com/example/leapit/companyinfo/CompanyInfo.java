package com.example.leapit.companyinfo;

import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "company_info_tb")
@Entity
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String logoImage;

    @Column(nullable = false)
    private String companyName;

    private LocalDate establishmentDate;

    @Column(nullable = false)
    private String address;

    @Lob
    private String mainService;

    @Lob
    private String introduction;

    @Column(nullable = false)
    private String image;

    @Lob
    private String benefit;
}

package com.example.leapit.companyinfo;

import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Builder;
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

    @Builder
    public CompanyInfo(Integer id, User user, String logoImage, String companyName, LocalDate establishmentDate, String address, String mainService, String introduction, String image, String benefit) {
        this.id = id;
        this.user = user;
        this.logoImage = logoImage;
        this.companyName = companyName;
        this.establishmentDate = establishmentDate;
        this.address = address;
        this.mainService = mainService;
        this.introduction = introduction;
        this.image = image;
        this.benefit = benefit;
    }
}

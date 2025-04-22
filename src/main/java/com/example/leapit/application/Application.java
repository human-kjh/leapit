package com.example.leapit.application;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "application_tb")
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer resumeId;
    private Integer jobPostingId;

    private LocalDate appliedDate;
    private Boolean isPassed;
    private Boolean isViewed= false;
}
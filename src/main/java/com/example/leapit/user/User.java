package com.example.leapit.user;

import com.example.leapit.common.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // PERSONAL / COMPANY

    @CreationTimestamp
    private Timestamp createdAt;

    // --- 개인 유저일 경우 ---
    private String name;
    private LocalDate birthDate;

    @Builder
    public User(String username, String password, String email, String contactNumber,
                Role role, String name, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.contactNumber = contactNumber;
        this.role = role;
        this.name = name;
        this.birthDate = birthDate;
    }

}
package com.example.leapit.resume;


import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "resume_tb")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private String title;

    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "position_type", nullable = false)
    private String positionType;

    @Column(columnDefinition = "TEXT")
    private String selfIntroduction;

    @Column(nullable = false)
    private Boolean isPublic = true;

    @CreationTimestamp
    private Timestamp createdAt;
}
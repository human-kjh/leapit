package com.example.leapit.application.bookmark;

import com.example.leapit.application.Application;
import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "application_bookmark_tb")
@Entity
public class ApplicationBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public ApplicationBookmark(Integer id, User user, Application application, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.application = application;
        this.createdAt = createdAt;
    }
}

package com.example.leapit.resume.link;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "link_tb")
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Builder
    public Link(Integer id, Resume resume, String title, String url) {
        this.id = id;
        this.resume = resume;
        this.title = title;
        this.url = url;
    }

    public void update(String title, String url) {
        this.title = title;
        this.url = url;
    }
}

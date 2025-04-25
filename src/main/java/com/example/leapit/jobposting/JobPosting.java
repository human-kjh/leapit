package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "job_posting_tb")
@Entity
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String positionType;

    private Integer minCareerLevel;
    private Integer maxCareerLevel;
    private String educationLevel;
    private Integer addressRegionId;
    private Integer addressSubRegionId;
    private String addressDetail;

    @Lob
    private String serviceIntro;

    @Column(nullable = false)
    private LocalDate deadline;

    @Lob
    @Column(nullable = false)
    private String responsibility;

    @Lob
    @Column(nullable = false)
    private String qualification;

    @Lob
    private String preference;

    @Lob
    private String benefit;

    @Lob
    private String additionalInfo;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer viewCount = 0;

    @CreationTimestamp
    private Timestamp createdAt;

    @ElementCollection
    @CollectionTable(name = "job_posting_tech_stack_tb", joinColumns = @JoinColumn(name = "job_posting_id"))
    @Column(name = "techStack", nullable = false)
    private List<String> techStack;


    @Builder
    public JobPosting(Integer id) {
        this.id = id;
    }

    @Builder
    public JobPosting(User user, String title, String positionType,
                      Integer minCareerLevel, Integer maxCareerLevel,
                      String educationLevel, Integer addressRegionId, Integer addressSubRegionId,
                      String addressDetail, String serviceIntro, LocalDate deadline,
                      String responsibility, String qualification, String preference,
                      String benefit, String additionalInfo, List<String> techStack) {
        this.user = user;
        this.title = title;
        this.positionType = positionType;
        this.minCareerLevel = minCareerLevel;
        this.maxCareerLevel = maxCareerLevel;
        this.educationLevel = educationLevel;
        this.addressRegionId = addressRegionId;
        this.addressSubRegionId = addressSubRegionId;
        this.addressDetail = addressDetail;
        this.serviceIntro = serviceIntro;
        this.deadline = deadline;
        this.responsibility = responsibility;
        this.qualification = qualification;
        this.preference = preference;
        this.benefit = benefit;
        this.additionalInfo = additionalInfo;
        this.techStack = techStack;
        this.viewCount = 0;
    }
}
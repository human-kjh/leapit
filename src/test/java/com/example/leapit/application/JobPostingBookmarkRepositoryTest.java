package com.example.leapit.application;

import com.example.leapit.jobposting.bookmark.JobPostingBookmark;
import com.example.leapit.jobposting.bookmark.JobPostingBookmarkRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JobPostingBookmarkRepository.class)
@DataJpaTest
public class JobPostingBookmarkRepositoryTest {

    @Autowired
    private JobPostingBookmarkRepository jobPostingBookmarkRepository;

    @Autowired
    private EntityManager em;


    @Test
    void testFindByUserIdAndJobPostingId() {
        int userId = 1;
        int jobPostingId = 2;

        JobPostingBookmark result = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(userId, jobPostingId);

        if (result != null) {
            System.out.println("✅ 조회 성공: " + result.getId());
            System.out.println("유저 ID: " + result.getUser().getId());
            System.out.println("공고 ID: " + result.getJobPosting().getId());
        } else {
            System.out.println("❌ 조회 실패: userId = " + userId + ", jobPostingId = " + jobPostingId);
        }
    }
}

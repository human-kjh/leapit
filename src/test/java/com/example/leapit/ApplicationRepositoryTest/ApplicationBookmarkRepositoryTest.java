package com.example.leapit.ApplicationRepositoryTest;

import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.application.bookmark.ApplicationBookmarkRepository;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@Import(ApplicationBookmarkRepository.class)
@DataJpaTest
public class ApplicationBookmarkRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private ApplicationBookmarkRepository applicationBookmarkRepository;
    @Test
    public void findAllJobPostingBookmarkByuserId_test() {
        // given
        Integer userId = 1;

        // when
        List<ApplicationBookmarkResponse.JobPostingBookmarkDTO> result =
                applicationBookmarkRepository.findAllJobPostingBookmarkByuserId(userId);

        // eye
        System.out.println("=== 지원자 조회 결과 ===");
        for (ApplicationBookmarkResponse.JobPostingBookmarkDTO dto : result) {
            System.out.println(dto.getJobPostingId());
            System.out.println(dto.getJobPostingTitle());
            System.out.println(dto.getCompanyName());
            System.out.println(dto.getDeadLine());
        }

    }
}

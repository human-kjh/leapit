package com.example.leapit.resume;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ResumeRepository.class)
@DataJpaTest
public class ResumeRepositoryTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void findAllById_test() {
        // given
        Integer userId = 1;

        // when
        List<Resume> resumeList = resumeRepository.findAllById(userId);

        // eye
        for (Resume resume : resumeList) {
            System.out.println("title : " + resume.getTitle());
        }
    }

}

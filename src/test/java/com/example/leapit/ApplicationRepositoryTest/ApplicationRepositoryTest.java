package com.example.leapit.ApplicationRepositoryTest;

import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.resume.ResumeRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(ApplicationRepository.class)
@DataJpaTest
public class ApplicationRepositoryTest {
}

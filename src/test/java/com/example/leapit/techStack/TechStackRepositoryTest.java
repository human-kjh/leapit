package com.example.leapit.techStack;


import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(TechStackRepository.class)
@DataJpaTest
public class TechStackRepositoryTest {

    @Autowired
    private TechStackRepository techStackRepository;

    @Test
    public void findAll_test() {

        // when
        List<TechStack> techs = techStackRepository.findAll();

        // eye
        for (TechStack tech : techs) {
            System.out.println(tech.getCode());
        }
    }

}

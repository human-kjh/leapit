package com.example.leapit.resume.experience.techstack;

import com.example.leapit.resume.experience.Experience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ExperienceTechStackService {
    private final ExperienceTechStackRepository experienceTechStackRepository;

    @Transactional
    public void update(Experience experience, List<String> updateTechStacks) {
        List<ExperienceTechStack> currentTechStacks = experience.getExperienceTechStacks();

        // 요청으로 받은 techStack 이름 리스트를 Set으로 변환 (빠른 검색용)
        Set<String> updateSet = new HashSet<>(updateTechStacks);

        // 1. 기존 ExperienceTechStack 중 요청에 없는 것은 삭제
        Iterator<ExperienceTechStack> iterator = currentTechStacks.iterator();
        while (iterator.hasNext()) {
            ExperienceTechStack existingTechStack = iterator.next();
            if (!updateSet.remove(existingTechStack.getTechStack())) {
                iterator.remove(); // 요청에 없으면 삭제
            }
        }

        // 2. 남은 updateSet에는 새로 추가할 스택만 남아 있음
        for (String techStackName : updateSet) {
            ExperienceTechStack newTechStack = ExperienceTechStack.builder()
                    .experience(experience)
                    .techStack(techStackName)
                    .build();
            currentTechStacks.add(newTechStack);
        }
    }
}

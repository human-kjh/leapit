package com.example.leapit.resume.techstack;

import com.example.leapit.resume.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ResumeTechStackService {
    private final ResumeTechStackRepository resumeTechStackRepository;

    @Transactional
    public void update(Resume resume, List<String> updateTechStacks) {
        List<ResumeTechStack> currentTechStacks = resume.getResumeTechStacks();

        Set<String> updateSet = new HashSet<>(updateTechStacks);

        // 1. 기존 ResumeTechStack 중, 요청에 없는 것은 삭제
        Iterator<ResumeTechStack> iterator = currentTechStacks.iterator();
        while (iterator.hasNext()) {
            ResumeTechStack existingTechStack = iterator.next();
            if (!updateSet.remove(existingTechStack.getTechStack())) {
                iterator.remove(); // 요청에 없는 것은 삭제
            }
        }

        // 2. 남은 updateSet에는 새로 추가할 스택만 남아 있음
        for (String techStackName : updateSet) {
            ResumeTechStack newTechStack = ResumeTechStack.builder()
                    .resume(resume)
                    .techStack(techStackName)
                    .build();
            currentTechStacks.add(newTechStack);
        }
    }
}

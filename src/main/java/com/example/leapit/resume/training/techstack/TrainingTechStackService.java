package com.example.leapit.resume.training.techstack;

import com.example.leapit.resume.training.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TrainingTechStackService {
    private final TrainingTechStackRepository trainingTechStackRepository;

    @Transactional
    public void update(Training training, List<String> updateTechStacks) {
        List<TrainingTechStack> currentTechStacks = training.getTrainingTechStacks();

        Set<String> updateSet = new HashSet<>(updateTechStacks);

        // 1. 기존 TechStack 중, 요청에 없는 것은 삭제
        Iterator<TrainingTechStack> iterator = currentTechStacks.iterator();
        while (iterator.hasNext()) {
            TrainingTechStack existingTechStack = iterator.next();
            if (!updateSet.remove(existingTechStack.getTechStack())) {
                iterator.remove(); // 요청에 없는 것은 삭제
            }
        }

        // 2. 남은 updateSet에는 새로 추가할 스택만 남아 있음
        for (String techStackName : updateSet) {
            TrainingTechStack newTechStack = TrainingTechStack.builder()
                    .training(training)
                    .techStack(techStackName)
                    .build();
            currentTechStacks.add(newTechStack);
        }
    }
}

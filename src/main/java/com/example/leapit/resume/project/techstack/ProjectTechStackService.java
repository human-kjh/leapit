package com.example.leapit.resume.project.techstack;

import com.example.leapit.resume.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ProjectTechStackService {
    private final ProjectTechStackRepository projectTechStackRepository;

    @Transactional
    public void update(Project project, List<String> updateTechStacks) {
        List<ProjectTechStack> currentTechStacks = project.getProjectTechStacks();

        // 요청으로 받은 techStack 이름 리스트를 Set으로 변환 (빠른 검색을 위해)
        Set<String> updateSet = new HashSet<>(updateTechStacks);

        // 1. 기존 ProjectTechStack 중, 요청에 없는 것은 삭제
        Iterator<ProjectTechStack> iterator = currentTechStacks.iterator();
        while (iterator.hasNext()) {
            ProjectTechStack existingTechStack = iterator.next();
            if (!updateSet.remove(existingTechStack.getTechStack())) {
                iterator.remove(); // 리스트에서 제거
            }
        }

        // 2. 남은 updateSet에는 새로 추가해야 할 techStack 이름만 남아 있음
        for (String techStackName : updateSet) {
            ProjectTechStack newTechStack = ProjectTechStack.builder()
                    .project(project)
                    .techStack(techStackName)
                    .build();
            currentTechStacks.add(newTechStack);
        }
    }
}

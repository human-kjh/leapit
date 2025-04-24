package com.example.leapit.resume.project;

import com.example.leapit.resume.project.techstack.ProjectTechStack;
import com.example.leapit.resume.project.techstack.ProjectTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectTechStackRepository projectTechStackRepository;

    public List<ProjectResponse.DetailDTO> getDTOsByResumeId(Integer resumeId) {
        List<Project> projectList = projectRepository.findAllByResumeId(resumeId);
        List<ProjectResponse.DetailDTO> dtoList = new ArrayList<>();

        for (Project project : projectList) {
            List<ProjectTechStack> techStacks = projectTechStackRepository.findAllByProjectId(project.getId());
            dtoList.add(new ProjectResponse.DetailDTO(project, techStacks));
        }
        return dtoList;
    }
}

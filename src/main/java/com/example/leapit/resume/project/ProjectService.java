package com.example.leapit.resume.project;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
import com.example.leapit.resume.project.techstack.ProjectTechStack;
import com.example.leapit.resume.project.techstack.ProjectTechStackRepository;
import com.example.leapit.resume.project.techstack.ProjectTechStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectTechStackRepository projectTechStackRepository;
    private final ProjectTechStackService projectTechStackService;

    public List<ProjectResponse.DetailDTO> getDTOsByResumeId(Integer resumeId) {
        List<Project> projectList = projectRepository.findAllByResumeId(resumeId);
        List<ProjectResponse.DetailDTO> dtoList = new ArrayList<>();

        for (Project project : projectList) {
            List<ProjectTechStack> techStacks = projectTechStackRepository.findAllByProjectId(project.getId());
            dtoList.add(new ProjectResponse.DetailDTO(project, techStacks));
        }
        return dtoList;
    }

    @Transactional
    public void update(Resume resumePS, List<ResumeRequest.UpdateDTO.ProjectDTO> projectDTOList) {
        List<Project> projectList = resumePS.getProjects();

        Map<Integer, ResumeRequest.UpdateDTO.ProjectDTO> dtoMap = projectDTOList.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.ProjectDTO::getId, dto -> dto));

        Iterator<Project> iterator = projectList.iterator();
        while (iterator.hasNext()) {
            Project project = iterator.next();
            ResumeRequest.UpdateDTO.ProjectDTO dto = dtoMap.remove(project.getId());

            if (dto != null) {
                project.update(
                        dto.getStartDate(), dto.getEndDate(), dto.getIsOngoing(),
                        dto.getTitle(), dto.getSummary(), dto.getDescription(), dto.getRepositoryUrl()
                );
                projectTechStackService.update(project, dto.getTechStacks());
            } else {
                iterator.remove();
            }
        }

        for (ResumeRequest.UpdateDTO.ProjectDTO dto : projectDTOList) {
            if (dto.getId() == null) {
                Project newProject = Project.builder()
                        .resume(resumePS)
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .isOngoing(dto.getIsOngoing())
                        .title(dto.getTitle())
                        .summary(dto.getSummary())
                        .description(dto.getDescription())
                        .repositoryUrl(dto.getRepositoryUrl())
                        .build();
                projectList.add(newProject);

                projectTechStackService.update(newProject, dto.getTechStacks());
            }
        }
    }
}

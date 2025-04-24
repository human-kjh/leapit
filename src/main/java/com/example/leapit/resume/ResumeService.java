package com.example.leapit.resume;

import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.positiontype.PositionTypeService;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.education.EducationRepository;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.etc.EtcRepository;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.experience.ExperienceRepository;
import com.example.leapit.resume.experience.ExperienceResponse;
import com.example.leapit.resume.experience.ExperienceService;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.link.LinkRepository;
import com.example.leapit.resume.project.ProjectResponse;
import com.example.leapit.resume.project.ProjectService;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.techstack.ResumeTechStackRepository;
import com.example.leapit.resume.training.TrainingResponse;
import com.example.leapit.resume.training.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final PositionTypeService positionTypeService;
    private final ResumeTechStackRepository resumeTechStackRepository;
    private final LinkRepository linkRepository;
    private final EducationRepository educationRepository;
    private final EtcRepository etcRepository;

    private final ExperienceService experienceService;
    private final TrainingService trainingService;
    private final ProjectService projectService;

    public List<Resume> list(int userId) {
        // 자신의 userId로 된 모든 resume을 찾아서 return
        return resumeRepository.findAllById(userId);
    }

    public ResumeResponse.DetailDTO detail(int resumeId) {

        Resume resume =  resumeRepository.findByIdJoinUser(resumeId);

        // 직무 : code -> label
        String code = resume.getPositionType();
        String label = positionTypeService.codeToLabel(code);

        List<ResumeTechStack> techStacks  = resumeTechStackRepository.findAllByResumeId(resumeId);

        List<Link> links = linkRepository.findAllByResumeId(resumeId);
        List<Education> educations = educationRepository.findAllByResumeId(resumeId) ;

        List<ExperienceResponse.DetailDTO> experiences = experienceService.getDTOsByResumeId(resumeId);
        List<ProjectResponse.DetailDTO> projects = projectService.getDTOsByResumeId(resumeId);
        List<TrainingResponse.DetailDTO> trainings = trainingService.getDTOsByResumeId(resumeId);

        List<Etc> etcs = etcRepository.findAllByResumeId(resumeId);


        ResumeResponse.DetailDTO detailDTO = new ResumeResponse.DetailDTO(resume, label, techStacks, links, educations, experiences, projects, trainings, etcs);

        return detailDTO;
    }
}

package com.example.leapit.resume;

import com.example.leapit._core.error.ex.Exception400;
import com.example.leapit._core.error.ex.Exception404;
import com.example.leapit._core.error.ex.ExceptionApi404;
import com.example.leapit.application.Application;
import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.education.EducationRepository;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.etc.EtcRepository;
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
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final PositionTypeRepository positionTypeRepository;
    private final TechStackRepository techStackRepository;
    private final ResumeTechStackRepository resumeTechStackRepository;
    private final LinkRepository linkRepository;
    private final EducationRepository educationRepository;
    private final EtcRepository etcRepository;

    private final ExperienceService experienceService;
    private final TrainingService trainingService;
    private final ProjectService projectService;

    private final ApplicationRepository applicationRepository;

    public List<Resume> list(Integer sessionUserId) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        // 자신의 userId로 된 모든 resume을 찾아서 return
        return resumeRepository.findAllByUserId(sessionUserId);
    }

    public ResumeResponse.DetailDTO detail(int resumeId) { // TODO : Integer sessionUserId 매개변수 추가
//        if(sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        // 1. 이력서 존재 확인
        Resume resume = resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new Exception404("이력서가 존재하지 않습니다.");

        // 2. 이력서 주인 (권한) 확인
//        if(!(resume.getUser().getId().equals(sessionUserId)) ) {
//            throw new Exception403("해당 이력서에 대한 권한이 없습니다.");
//        }

        // 3. 이력서 DTO 조립
        List<ResumeTechStack> techStacks = resumeTechStackRepository.findAllByResumeId(resumeId);
        List<Link> links = linkRepository.findAllByResumeId(resumeId);
        List<Education> educations = educationRepository.findAllByResumeId(resumeId);
        List<ExperienceResponse.DetailDTO> experiences = experienceService.getDTOsByResumeId(resumeId);
        List<ProjectResponse.DetailDTO> projects = projectService.getDTOsByResumeId(resumeId);
        List<TrainingResponse.DetailDTO> trainings = trainingService.getDTOsByResumeId(resumeId);
        List<Etc> etcs = etcRepository.findAllByResumeId(resumeId);

        ResumeResponse.DetailDTO detailDTO = new ResumeResponse.DetailDTO(resume, techStacks, links, educations, experiences, projects, trainings, etcs);

        return detailDTO;
    }

    @Transactional
    public void delete(int resumeId) { // TODO : Integer sessionUserId 매개변수 추가
//        if(sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        // 1. 이력서 존재 확인
        Resume resume = resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new Exception404("이력서가 존재하지 않습니다.");

        // 2. 지원된 이력서인지 확인
        // 연관된 지원서 존재 여부 확인
        List<Application> applications = applicationRepository.findAllByResumeId(resumeId);
        if (applications != null && !applications.isEmpty()) {
            throw new Exception400("이 이력서는 지원 이력이 있어 삭제할 수 없습니다.");
        }

        // 2. 이력서 주인 (권한) 확인
//        if(!(resume.getUser().getId().equals(sessionUserId)) ) {
//            throw new Exception403("해당 이력서에 대한 권한이 없습니다.");
//        }

        // 3. 이력서 삭제
        resumeRepository.deleteById(resumeId);
    }

    public ResumeResponse.SaveDTO getSaveForm(Integer sessionUserId) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다");
        User user = userRepository.findById(sessionUserId);
        List<PositionType> positionTypes = positionTypeRepository.findAll();
        List<TechStack> techStacks = techStackRepository.findAll();
        return new ResumeResponse.SaveDTO(user, positionTypes, techStacks);
    }

    @Transactional
    public void save(ResumeRequest.SaveDTO saveDTO, User sessionUser) {
        if (sessionUser == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다");

        Resume resume = saveDTO.toEntity(sessionUser);
        resumeRepository.save(resume);
    }
}

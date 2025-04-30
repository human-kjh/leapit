package com.example.leapit.resume;

import com.example.leapit.application.Application;
import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.positiontype.PositionTypeService;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.education.EducationRepository;
import com.example.leapit.resume.education.EducationService;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.etc.EtcRepository;
import com.example.leapit.resume.etc.EtcService;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.experience.ExperienceRepository;
import com.example.leapit.resume.experience.ExperienceResponse;
import com.example.leapit.resume.experience.ExperienceService;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.link.LinkRepository;
import com.example.leapit.resume.link.LinkService;
import com.example.leapit.resume.project.Project;
import com.example.leapit.resume.project.ProjectRepository;
import com.example.leapit.resume.project.ProjectResponse;
import com.example.leapit.resume.project.ProjectService;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.techstack.ResumeTechStackRepository;
import com.example.leapit.resume.techstack.ResumeTechStackService;
import com.example.leapit.resume.training.Training;
import com.example.leapit.resume.training.TrainingRepository;
import com.example.leapit.resume.training.TrainingResponse;
import com.example.leapit.resume.training.TrainingService;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final ResumeTechStackService resumeTechStackService;
    private final ExperienceService experienceService;
    private final TrainingService trainingService;
    private final ProjectService projectService;
    private final EducationService educationService;
    private final EtcService etcService;
    private final LinkService linkService;

    private final ApplicationRepository applicationRepository;
    private final ExperienceRepository experienceRepository;
    private final TrainingRepository trainingRepository;
    private final ProjectRepository projectRepository;

    public List<Resume> list(Integer sessionUserId) {
        // 자신의 userId로 된 모든 resume을 찾아서 return
        return resumeRepository.findAllByUserId(sessionUserId);
    }

    public ResumeResponse.DetailDTO detail(int resumeId) { // TODO : Integer sessionUserId 매개변수 추가
        // 1. 이력서 존재 확인
        Resume resume =  resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new RuntimeException("이력서가 존재하지 않습니다.");

        // 2. 이력서 주인 (권한) 확인
//        if(!(resume.getUser().getId().equals(sessionUserId)) ) {
//            throw new RuntimeException("해당 이력서에 대한 권한이 없습니다.");
//        }

        // 3. 이력서 DTO 조립
        List<ResumeTechStack> techStacks  = resumeTechStackRepository.findAllByResumeId(resumeId);
        List<Link> links = linkRepository.findAllByResumeId(resumeId);
        List<Education> educations = educationRepository.findAllByResumeId(resumeId) ;
        List<ExperienceResponse.DetailDTO> experiences = experienceService.getDTOsByResumeId(resumeId);
        List<ProjectResponse.DetailDTO> projects = projectService.getDTOsByResumeId(resumeId);
        List<TrainingResponse.DetailDTO> trainings = trainingService.getDTOsByResumeId(resumeId);
        List<Etc> etcs = etcRepository.findAllByResumeId(resumeId);

        ResumeResponse.DetailDTO detailDTO = new ResumeResponse.DetailDTO(resume, techStacks, links, educations, experiences, projects, trainings, etcs);

        return detailDTO;
    }

    @Transactional
    public void delete(int resumeId) { // TODO : Integer sessionUserId 매개변수 추가
        // 1. 이력서 존재 확인
        Resume resume =  resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new RuntimeException("이력서가 존재하지 않습니다.");

        // 2. 지원된 이력서인지 확인
        // 연관된 지원서 존재 여부 확인
        List<Application> applications = applicationRepository.findAllByResumeId(resumeId);
        if (applications != null && !applications.isEmpty()) {
            throw new IllegalStateException("이 이력서는 지원 이력이 있어 삭제할 수 없습니다.");
        }

        // 2. 이력서 주인 (권한) 확인
//        if(!(resume.getUser().getId().equals(sessionUserId)) ) {
//            throw new RuntimeException("해당 이력서에 대한 권한이 없습니다.");
//        }

        // 3. 이력서 삭제
        resumeRepository.deleteById(resumeId);
    }

    public ResumeResponse.SaveDTO getSaveForm(Integer sessionUserId){
        User user = userRepository.findById(sessionUserId);
        List<PositionType> positionTypes = positionTypeRepository.findAll();
        List<TechStack> techStacks = techStackRepository.findAll();
        return new ResumeResponse.SaveDTO(user, positionTypes, techStacks);
    }

    @Transactional
    public void save(ResumeRequest.SaveDTO saveDTO, User sessionUser) {
        Resume resume = saveDTO.toEntity(sessionUser);
        resumeRepository.save(resume);
    }

    @Transactional
    public void update(Integer resumeId, ResumeRequest.UpdateDTO reqDTO){
        // 1. 이력서 존재 확인
        Resume resumePS = resumeRepository.findById(resumeId);
        if (resumePS == null) throw new RuntimeException("이력서가 존재하지 않습니다.");

        // 2. (선택) 권한 확인
        // if (!(resumePS.getUser().getId().equals(sessionUserId))) {
        //     throw new RuntimeException("해당 이력서에 대한 권한이 없습니다.");
        // }

        // 3. 이력서 업데이트
        resumePS.update(reqDTO.getTitle(), reqDTO.getPhotoUrl(), reqDTO.getIsPublic(), reqDTO.getSummary(), reqDTO.getPositionType(), reqDTO.getSelfIntroduction());

        // 항목별 업데이트는 각 항목의 Service 호출
        resumeTechStackService.update(resumePS, reqDTO.getResumeTechStacks());
        trainingService.update(resumePS, reqDTO.getTrainings());
        projectService.update(resumePS, reqDTO.getProjects());
        experienceService.update(resumePS, reqDTO.getExperiences());
        educationService.update(resumePS, reqDTO.getEducations());
        linkService.update(resumePS, reqDTO.getLinks());
        etcService.update(resumePS, reqDTO.getEtcs());

    }


    public ResumeResponse.UpdateDTO getUpdateForm(Integer resumeId) {
        // 1. 해당 이력서 존재 확인
        Resume resume = resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new RuntimeException("이력서가 존재하지 않습니다.");

        // 2. (선택) 권한 확인
        // if (!(resume.getUser().getId().equals(sessionUserId))) {
        //     throw new RuntimeException("해당 이력서에 대한 권한이 없습니다.");
        // }

        // 3. 이력서 관련 데이터 조회

        // 전체 기술스택
        List<TechStack> allTechStacks = techStackRepository.findAll();

        // 사용자가 선택한 기술스택 (ResumeTechStack → String 리스트)
        List<ResumeTechStack> resumeTechStacks = resumeTechStackRepository.findAllByResumeId(resumeId);
        List<String> selectedTechStackCodes = new ArrayList<>();
        for (ResumeTechStack tech : resumeTechStacks) {
            selectedTechStackCodes.add(tech.getTechStack());
        }

        // 전체 기술스택 → ResumeTechStackDTO 변환 (checked 여부 포함)
        List<ResumeResponse.UpdateDTO.ResumeTechStackDTO> resumeTechStackDTOList = new ArrayList<>();
        for (TechStack techStack : allTechStacks) {
            boolean isChecked = false;
            for (String selectedCode : selectedTechStackCodes) {
                if (techStack.getCode().equals(selectedCode)) {
                    isChecked = true;
                    break;
                }
            }
            resumeTechStackDTOList.add(new ResumeResponse.UpdateDTO.ResumeTechStackDTO(techStack.getCode(), isChecked));
        }

        // 전체 직무 포지션
        List<PositionType> positionTypes = positionTypeRepository.findAll();

        // 링크
        List<Link> linkList = linkRepository.findAllByResumeId(resumeId);
        List<ResumeResponse.UpdateDTO.LinkDTO> linkDTOList = new ArrayList<>();
        for (Link link : linkList) {
            linkDTOList.add(new ResumeResponse.UpdateDTO.LinkDTO(link));
        }

        // 학력
        List<Education> educationList = educationRepository.findAllByResumeId(resumeId);
        List<ResumeResponse.UpdateDTO.EducationDTO> educationDTOList = new ArrayList<>();
        for (Education education : educationList) {
            educationDTOList.add(new ResumeResponse.UpdateDTO.EducationDTO(education));
        }

        // 경력
        List<Experience> experienceList = experienceRepository.findAllByResumeId(resumeId);
        List<ResumeResponse.UpdateDTO.ExperienceDTO> experienceDTOList = new ArrayList<>();
        for (Experience experience : experienceList) {
            experienceDTOList.add(new ResumeResponse.UpdateDTO.ExperienceDTO(experience, allTechStacks));
        }

        // 프로젝트
        List<Project> projectList = projectRepository.findAllByResumeId(resumeId);
        List<ResumeResponse.UpdateDTO.ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project project : projectList) {
            projectDTOList.add(new ResumeResponse.UpdateDTO.ProjectDTO(project, allTechStacks));
        }

        // 교육이력
        List<Training> trainingList = trainingRepository.findAllByResumeId(resumeId);
        List<ResumeResponse.UpdateDTO.TrainingDTO> trainingDTOList = new ArrayList<>();
        for (Training training : trainingList) {
            trainingDTOList.add(new ResumeResponse.UpdateDTO.TrainingDTO(training, allTechStacks));
        }

        // 기타사항
        List<Etc> etcList = etcRepository.findAllByResumeId(resumeId);
        List<ResumeResponse.UpdateDTO.EtcDTO> etcDTOList = new ArrayList<>();
        for (Etc etc : etcList) {
            etcDTOList.add(new ResumeResponse.UpdateDTO.EtcDTO(etc));
        }

        // 4. UpdateDTO 조립 후 리턴
        ResumeResponse.UpdateDTO updateDTO = new ResumeResponse.UpdateDTO(
                resume,
                positionTypes,
                allTechStacks,
                resumeTechStackDTOList,
                linkDTOList,
                educationDTOList,
                experienceDTOList,
                projectDTOList,
                trainingDTOList,
                etcDTOList
        );
        return updateDTO;
    }
}

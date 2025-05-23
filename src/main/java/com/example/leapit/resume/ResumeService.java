package com.example.leapit.resume;

import com.example.leapit._core.error.ex.Exception400;
import com.example.leapit._core.error.ex.Exception403;
import com.example.leapit._core.error.ex.Exception404;
import com.example.leapit._core.error.ex.ExceptionApi400;
import com.example.leapit.application.Application;
import com.example.leapit.application.ApplicationRepository;
import com.example.leapit.common.enums.Role;
import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.resume.education.Education;
import com.example.leapit.resume.education.EducationRepository;
import com.example.leapit.resume.education.EducationResponse;
import com.example.leapit.resume.education.EducationService;
import com.example.leapit.resume.etc.Etc;
import com.example.leapit.resume.etc.EtcRepository;
import com.example.leapit.resume.etc.EtcResponse;
import com.example.leapit.resume.etc.EtcService;
import com.example.leapit.resume.experience.Experience;
import com.example.leapit.resume.experience.ExperienceRepository;
import com.example.leapit.resume.experience.ExperienceResponse;
import com.example.leapit.resume.experience.ExperienceService;
import com.example.leapit.resume.link.Link;
import com.example.leapit.resume.link.LinkRepository;
import com.example.leapit.resume.link.LinkResponse;
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
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<ResumeResponse.ListDTO> list(Integer sessionUserId) {
        // 자신의 userId로 된 모든 resume을 찾아서 return
        List<Resume> resumes = resumeRepository.findAllByUserId(sessionUserId);
        List<ResumeResponse.ListDTO> listDTOs = new ArrayList<>();
        for (Resume resume : resumes) {
            listDTOs.add(new ResumeResponse.ListDTO(resume));
        }
        return listDTOs;
    }

    public ResumeResponse.DetailDTO detail(Integer resumeId, User sessionUser, @Nullable Integer applicationId) {
        // 1. 이력서 존재 확인
        Resume resume = resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new Exception404("이력서가 존재하지 않습니다.");

        // 2. 권한 체크
        if (sessionUser.getRole() == Role.personal) {
            // 개인 유저는 본인만 열람 가능
            if (!(resume.getUser().getId().equals(sessionUser.getId()))) {
                throw new Exception403("해당 이력서에 대한 권한이 없습니다.");
            }
        } else if (sessionUser.getRole() == Role.company) {
            // 기업 유저는 applicationId를 통해 접근한 경우만 허용
            if (applicationId == null) {
                throw new Exception403("applicationId가 필요합니다.");
            }

            Application application = applicationRepository.findByApplicationId(applicationId);
            if (application == null ||
                    !application.getResume().getId().equals(resumeId) ||
                    !application.getJobPosting().getUser().getId().equals(sessionUser.getId())) {
                throw new Exception403("해당 이력서를 열람할 권한이 없습니다.");
            }

        }

        // 3. 이력서 DTO 조립
        List<ResumeTechStack> techStacks = resumeTechStackRepository.findAllByResumeId(resumeId);
        List<LinkResponse.DetailDTO> links = linkService.getDTOsByResumeId(resumeId);
        List<EducationResponse.DetailDTO> educations = educationService.getDTOsByResumeId(resumeId);
        List<ExperienceResponse.DetailDTO> experiences = experienceService.getDTOsByResumeId(resumeId);
        List<ProjectResponse.DetailDTO> projects = projectService.getDTOsByResumeId(resumeId);
        List<TrainingResponse.DetailDTO> trainings = trainingService.getDTOsByResumeId(resumeId);
        List<EtcResponse.DetailDTO> etcs = etcService.getDTOsByResumeId(resumeId);

        ResumeResponse.DetailDTO detailDTO = new ResumeResponse.DetailDTO(resume, techStacks, links, educations, experiences, projects, trainings, etcs);

        return detailDTO;
    }

    @Transactional
    public void delete(Integer resumeId, Integer sessionUserId) { // TODO : Integer sessionUserId 매개변수 추가
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
        if (!(resume.getUser().getId().equals(sessionUserId))) {
            throw new Exception403("해당 이력서에 대한 권한이 없습니다.");
        }

        // 3. 이력서 삭제
        resumeRepository.deleteById(resumeId);
    }

    public ResumeResponse.SaveDTO getSaveForm(Integer sessionUserId) {
        User user = userRepository.findById(sessionUserId);
        List<PositionType> positionTypes = positionTypeRepository.findAll();
        List<TechStack> techStacks = techStackRepository.findAll();
        return new ResumeResponse.SaveDTO(user, positionTypes, techStacks);
    }

    @Transactional
    public void save(ResumeRequest.SaveDTO saveDTO, MultipartFile photoUrlFile, User sessionUser) {
        // 이미지
        String uploadDir = System.getProperty("user.dir") + "/upload/";
        try {
            // 디렉토리 없을 경우 생성
            Files.createDirectories(Paths.get(uploadDir));

            // 대표 이미지 저장
            if (photoUrlFile != null && !photoUrlFile.isEmpty()) {
                String imageFilename = UUID.randomUUID() + "_" + photoUrlFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadDir + imageFilename);
                Files.write(imagePath, photoUrlFile.getBytes());
                saveDTO.setPhotoUrl(imageFilename);
            }

        } catch (Exception e) {
            throw new Exception400("파일 업로드 실패");
        }

        // 유효성 검사 (조건부 포함)
        validateEducationsSave(saveDTO.getEducations());
        validateTrainingsSave(saveDTO.getTrainings());
        validateEtcsSave(saveDTO.getEtcs());
        validateLinksSave(saveDTO.getLinks());
        validateExperiencesSave(saveDTO.getExperiences());
        validateResumeBasicsSave(saveDTO);

        Resume resume = saveDTO.toEntity(sessionUser);
        resumeRepository.save(resume);
    }

    @Transactional
    public void update(Integer resumeId, Integer sessionUserId, ResumeRequest.UpdateDTO reqDTO, MultipartFile photoUrlFile) {
        // 1. 이력서 존재 확인
        Resume resumePS = resumeRepository.findById(resumeId);
        if (resumePS == null) throw new Exception404("이력서가 존재하지 않습니다.");

        // 2. 권한 확인
        if (!(resumePS.getUser().getId().equals(sessionUserId))) {
            throw new Exception403("해당 이력서에 대한 권한이 없습니다.");
        }

        // 이미지
        if (photoUrlFile != null && !photoUrlFile.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/upload/";
            try {
                Files.createDirectories(Paths.get(uploadDir));
                String imageFilename = UUID.randomUUID() + "_" + photoUrlFile.getOriginalFilename();
                Path imagePath = Paths.get(uploadDir + imageFilename);
                Files.write(imagePath, photoUrlFile.getBytes());

                reqDTO.setPhotoUrl(imageFilename);
            } catch (Exception e) {
                throw new Exception400("파일 업로드 실패");
            }
        }
        // 유효성 검사
        validateEducationsUpdate(reqDTO.getEducations());
        validateTrainingsUpdate(reqDTO.getTrainings());
        validateEtcsUpdate(reqDTO.getEtcs());
        validateLinksUpdate(reqDTO.getLinks());
        validateExperiencesUpdate(reqDTO.getExperiences());
        validateResumeBasicsUpdate(reqDTO);


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


    public ResumeResponse.UpdateDTO getUpdateForm(Integer resumeId, Integer sessionUserId) {
        // 1. 해당 이력서 존재 확인
        Resume resume = resumeRepository.findByIdJoinUser(resumeId);
        if (resume == null) throw new Exception404("이력서가 존재하지 않습니다.");

        // 2. 권한 확인
        if (!(resume.getUser().getId().equals(sessionUserId))) {
            throw new Exception403("해당 이력서에 대한 권한이 없습니다.");
        }

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

    // ---------- 이력서 등록 시 선택항목의 필수값 유효성검사
    private void validateResumeBasicsSave(ResumeRequest.SaveDTO dto) {
        if (dto.getPhotoUrl() != null && dto.getPhotoUrl().isBlank()) {
            throw new ExceptionApi400("이미지를 선택했다면 파일명이 필요합니다.");
        }
        if (dto.getSummary() != null && dto.getSummary().isBlank()) {
            throw new ExceptionApi400("요약을 작성했다면 내용을 입력해야 합니다.");
        }
    }

    private void validateEtcsSave(List<ResumeRequest.SaveDTO.EtcDTO> etcs) {
        for (ResumeRequest.SaveDTO.EtcDTO etc : etcs) {
            if ((etc.getTitle() != null && !etc.getTitle().isBlank()) ||
                    (etc.getEtcType() != null && !etc.getEtcType().isBlank()) ||
                    (etc.getInstitutionName() != null && !etc.getInstitutionName().isBlank()) ||
                    (etc.getDescription() != null && !etc.getDescription().isBlank())) {

                if (etc.getStartDate() == null) {
                    throw new ExceptionApi400("기타 항목의 시작일은 필수입니다.");
                }
                if (etc.getEndDate() == null) {
                    throw new ExceptionApi400("기타 항목의 종료일은 필수입니다.");
                }
                if (etc.getTitle() == null || etc.getTitle().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 제목은 필수입니다.");
                }
                if (etc.getEtcType() == null || etc.getEtcType().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 유형은 필수입니다.");
                }
                if (etc.getInstitutionName() == null || etc.getInstitutionName().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 기관명은 필수입니다.");
                }
                if (etc.getDescription() == null || etc.getDescription().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 설명은 필수입니다.");
                }
            }
        }
    }

    private void validateEducationsSave(List<ResumeRequest.SaveDTO.EducationDTO> educations) {
        for (ResumeRequest.SaveDTO.EducationDTO edu : educations) {
            // 조건부 필수값
            if (edu.getEducationLevel().equals("대졸") || edu.getEducationLevel().equals("대학교")) {
                if (edu.getMajor() == null || edu.getMajor().isBlank()) {
                    throw new ExceptionApi400("전공은 필수입니다.");
                }
                if (edu.getGpa() == null) {
                    throw new ExceptionApi400("학점은 필수입니다.");
                }
                if (edu.getGpaScale() == null) {
                    throw new ExceptionApi400("학점 만점 기준은 필수입니다.");
                }
            }
        }
    }


    private void validateExperiencesSave(List<ResumeRequest.SaveDTO.ExperienceDTO> experiences) {
        for (ResumeRequest.SaveDTO.ExperienceDTO exp : experiences) {
            // 사용자가 경력을 작성했는지 판단할 기준
            boolean isFilled =
                    (exp.getCompanyName() != null && !exp.getCompanyName().isBlank()) ||
                            (exp.getResponsibility() != null && !exp.getResponsibility().isBlank()) ||
                            (exp.getTechStacks() != null && !exp.getTechStacks().isEmpty());

            if (isFilled) {
                if (exp.getStartDate() == null) {
                    throw new ExceptionApi400("경력 시작일은 필수입니다.");
                }
                if (exp.getEndDate() == null) {
                    throw new ExceptionApi400("경력 종료일은 필수입니다.");
                }
                if (exp.getCompanyName() == null || exp.getCompanyName().isBlank()) {
                    throw new ExceptionApi400("회사명은 필수입니다.");
                }
                if (exp.getResponsibility() == null || exp.getResponsibility().isBlank()) {
                    throw new ExceptionApi400("담당 업무는 필수입니다.");
                }
                if (exp.getTechStacks() == null || exp.getTechStacks().isEmpty()) {
                    throw new ExceptionApi400("기술 스택은 최소 1개 이상 입력해야 합니다.");
                }
            }
        }
    }


    private void validateLinksSave(List<ResumeRequest.SaveDTO.LinkDTO> links) {
        for (ResumeRequest.SaveDTO.LinkDTO link : links) {
            // 사용자가 작성했는지 여부 판단 (둘 중 하나라도 작성 시 유효성 검사 시작)
            boolean isFilled =
                    (link.getTitle() != null && !link.getTitle().isBlank()) ||
                            (link.getUrl() != null && !link.getUrl().isBlank());

            if (isFilled) {
                if (link.getTitle() == null || link.getTitle().isBlank()) {
                    throw new ExceptionApi400("링크 제목은 필수입니다.");
                }
                if (link.getUrl() == null || link.getUrl().isBlank()) {
                    throw new ExceptionApi400("링크 URL은 필수입니다.");
                }
            }
        }
    }

    private void validateTrainingsSave(List<ResumeRequest.SaveDTO.TrainingDTO> trainings) {
        for (ResumeRequest.SaveDTO.TrainingDTO training : trainings) {
            // 작성 여부 판단 (주요 필드 중 하나라도 입력되어 있으면 검사)
            boolean isFilled =
                    (training.getCourseName() != null && !training.getCourseName().isBlank()) ||
                            (training.getInstitutionName() != null && !training.getInstitutionName().isBlank()) ||
                            (training.getDescription() != null && !training.getDescription().isBlank()) ||
                            (training.getTechStacks() != null && !training.getTechStacks().isEmpty());

            if (isFilled) {
                if (training.getStartDate() == null) {
                    throw new ExceptionApi400("교육 시작일은 필수입니다.");
                }
                if (training.getEndDate() == null) {
                    throw new ExceptionApi400("교육 종료일은 필수입니다.");
                }
                if (training.getCourseName() == null || training.getCourseName().isBlank()) {
                    throw new ExceptionApi400("과정명은 필수입니다.");
                }
                if (training.getInstitutionName() == null || training.getInstitutionName().isBlank()) {
                    throw new ExceptionApi400("교육 기관명은 필수입니다.");
                }
                if (training.getDescription() == null || training.getDescription().isBlank()) {
                    throw new ExceptionApi400("교육 설명은 필수입니다.");
                }
                if (training.getTechStacks() == null || training.getTechStacks().isEmpty()) {
                    throw new ExceptionApi400("기술 스택은 1개 이상 입력해야 합니다.");
                }
            }
        }
    }

    // ---------- 이력서 수정시 선택항목의 필수값 유효성검사

    private void validateResumeBasicsUpdate(ResumeRequest.UpdateDTO dto) {
        if (dto.getPhotoUrl() != null && dto.getPhotoUrl().isBlank()) {
            throw new ExceptionApi400("이미지를 선택했다면 파일명이 필요합니다.");
        }
        if (dto.getSummary() != null && dto.getSummary().isBlank()) {
            throw new ExceptionApi400("요약을 작성했다면 내용을 입력해야 합니다.");
        }
    }

    private void validateEtcsUpdate(List<ResumeRequest.UpdateDTO.EtcDTO> etcs) {
        for (ResumeRequest.UpdateDTO.EtcDTO etc : etcs) {
            if ((etc.getTitle() != null && !etc.getTitle().isBlank()) ||
                    (etc.getEtcType() != null && !etc.getEtcType().isBlank()) ||
                    (etc.getInstitutionName() != null && !etc.getInstitutionName().isBlank()) ||
                    (etc.getDescription() != null && !etc.getDescription().isBlank())) {

                if (etc.getStartDate() == null) {
                    throw new ExceptionApi400("기타 항목의 시작일은 필수입니다.");
                }
                if (etc.getEndDate() == null) {
                    throw new ExceptionApi400("기타 항목의 종료일은 필수입니다.");
                }
                if (etc.getTitle() == null || etc.getTitle().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 제목은 필수입니다.");
                }
                if (etc.getEtcType() == null || etc.getEtcType().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 유형은 필수입니다.");
                }
                if (etc.getInstitutionName() == null || etc.getInstitutionName().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 기관명은 필수입니다.");
                }
                if (etc.getDescription() == null || etc.getDescription().isBlank()) {
                    throw new ExceptionApi400("기타 항목의 설명은 필수입니다.");
                }
            }
        }
    }

    private void validateEducationsUpdate(List<ResumeRequest.UpdateDTO.EducationDTO> educations) {
        for (ResumeRequest.UpdateDTO.EducationDTO edu : educations) {
            // 조건부 필수값
            if (edu.getEducationLevel().equals("대졸") || edu.getEducationLevel().equals("대학교")) {
                if (edu.getMajor() == null || edu.getMajor().isBlank()) {
                    throw new ExceptionApi400("전공은 필수입니다.");
                }
                if (edu.getGpa() == null) {
                    throw new ExceptionApi400("학점은 필수입니다.");
                }
                if (edu.getGpaScale() == null) {
                    throw new ExceptionApi400("학점 만점 기준은 필수입니다.");
                }
            }
        }
    }


    private void validateExperiencesUpdate(List<ResumeRequest.UpdateDTO.ExperienceDTO> experiences) {
        for (ResumeRequest.UpdateDTO.ExperienceDTO exp : experiences) {
            // 사용자가 경력을 작성했는지 판단할 기준
            boolean isFilled =
                    (exp.getCompanyName() != null && !exp.getCompanyName().isBlank()) ||
                            (exp.getResponsibility() != null && !exp.getResponsibility().isBlank()) ||
                            (exp.getTechStacks() != null && !exp.getTechStacks().isEmpty());

            if (isFilled) {
                if (exp.getStartDate() == null) {
                    throw new ExceptionApi400("경력 시작일은 필수입니다.");
                }
                if (exp.getEndDate() == null) {
                    throw new ExceptionApi400("경력 종료일은 필수입니다.");
                }
                if (exp.getCompanyName() == null || exp.getCompanyName().isBlank()) {
                    throw new ExceptionApi400("회사명은 필수입니다.");
                }
                if (exp.getResponsibility() == null || exp.getResponsibility().isBlank()) {
                    throw new ExceptionApi400("담당 업무는 필수입니다.");
                }
                if (exp.getTechStacks() == null || exp.getTechStacks().isEmpty()) {
                    throw new ExceptionApi400("기술 스택은 최소 1개 이상 입력해야 합니다.");
                }
            }
        }
    }


    private void validateLinksUpdate(List<ResumeRequest.UpdateDTO.LinkDTO> links) {
        for (ResumeRequest.UpdateDTO.LinkDTO link : links) {
            // 사용자가 작성했는지 여부 판단 (둘 중 하나라도 작성 시 유효성 검사 시작)
            boolean isFilled =
                    (link.getTitle() != null && !link.getTitle().isBlank()) ||
                            (link.getUrl() != null && !link.getUrl().isBlank());

            if (isFilled) {
                if (link.getTitle() == null || link.getTitle().isBlank()) {
                    throw new ExceptionApi400("링크 제목은 필수입니다.");
                }
                if (link.getUrl() == null || link.getUrl().isBlank()) {
                    throw new ExceptionApi400("링크 URL은 필수입니다.");
                }
            }
        }
    }

    private void validateTrainingsUpdate(List<ResumeRequest.UpdateDTO.TrainingDTO> trainings) {
        for (ResumeRequest.UpdateDTO.TrainingDTO training : trainings) {
            // 작성 여부 판단 (주요 필드 중 하나라도 입력되어 있으면 검사)
            boolean isFilled =
                    (training.getCourseName() != null && !training.getCourseName().isBlank()) ||
                            (training.getInstitutionName() != null && !training.getInstitutionName().isBlank()) ||
                            (training.getDescription() != null && !training.getDescription().isBlank()) ||
                            (training.getTechStacks() != null && !training.getTechStacks().isEmpty());

            if (isFilled) {
                if (training.getStartDate() == null) {
                    throw new ExceptionApi400("교육 시작일은 필수입니다.");
                }
                if (training.getEndDate() == null) {
                    throw new ExceptionApi400("교육 종료일은 필수입니다.");
                }
                if (training.getCourseName() == null || training.getCourseName().isBlank()) {
                    throw new ExceptionApi400("과정명은 필수입니다.");
                }
                if (training.getInstitutionName() == null || training.getInstitutionName().isBlank()) {
                    throw new ExceptionApi400("교육 기관명은 필수입니다.");
                }
                if (training.getDescription() == null || training.getDescription().isBlank()) {
                    throw new ExceptionApi400("교육 설명은 필수입니다.");
                }
                if (training.getTechStacks() == null || training.getTechStacks().isEmpty()) {
                    throw new ExceptionApi400("기술 스택은 1개 이상 입력해야 합니다.");
                }
            }
        }
    }


}


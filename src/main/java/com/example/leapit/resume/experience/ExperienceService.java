package com.example.leapit.resume.experience;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import com.example.leapit.resume.experience.techstack.ExperienceTechStackRepository;
import com.example.leapit.resume.experience.techstack.ExperienceTechStackService;
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
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceTechStackService experienceTechStackService;


    private final ExperienceTechStackRepository experienceTechStackRepository;

    public List<ExperienceResponse.DetailDTO> getDTOsByResumeId(Integer resumeId){
        List<Experience> experienceList = experienceRepository.findAllByResumeId(resumeId);
        List<ExperienceResponse.DetailDTO> dtoList = new ArrayList<>();


        for (Experience experience : experienceList) {
            List<ExperienceTechStack> techStacks = experienceTechStackRepository.findAllByExperienceId(experience.getId());
            dtoList.add(new ExperienceResponse.DetailDTO(experience, techStacks));
        }

        return dtoList;
    }

    @Transactional
    public void update(Resume resumePS, List<ResumeRequest.UpdateDTO.ExperienceDTO> experienceDTOList) {
        List<Experience> experienceList = resumePS.getExperiences();

        // 요청에서 id가 있는 DTO들만 Map으로 변환
        Map<Integer, ResumeRequest.UpdateDTO.ExperienceDTO> dtoMap = experienceDTOList.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.ExperienceDTO::getId, dto -> dto));

        // 1. 기존 Experience 리스트 돌면서 수정 or 삭제
        Iterator<Experience> iterator = experienceList.iterator();
        while (iterator.hasNext()) {
            Experience experience = iterator.next();
            ResumeRequest.UpdateDTO.ExperienceDTO dto = dtoMap.remove(experience.getId());

            if (dto != null) {
                // 수정 대상이면 update
                experience.update(
                        dto.getStartDate(),
                        dto.getEndDate(),
                        dto.getIsEmployed(),
                        dto.getCompanyName(),
                        dto.getSummary(),
                        dto.getPosition(),
                        dto.getResponsibility()
                );

                experienceTechStackService.update(experience, dto.getTechStacks());
            } else {
                // 요청에 없으면 삭제
                iterator.remove();
            }
        }

        // 2. 요청 데이터 중 id가 없는 것은 신규 추가
        for (ResumeRequest.UpdateDTO.ExperienceDTO dto : experienceDTOList) {
            if (dto.getId() == null) {
                Experience newExperience = Experience.builder()
                        .resume(resumePS)
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .isEmployed(dto.getIsEmployed())
                        .companyName(dto.getCompanyName())
                        .summary(dto.getSummary())
                        .position(dto.getPosition())
                        .responsibility(dto.getResponsibility())
                        .build();
                experienceList.add(newExperience);

                experienceTechStackService.update(newExperience, dto.getTechStacks());
            }
        }
    }


}

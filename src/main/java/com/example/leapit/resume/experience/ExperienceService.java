package com.example.leapit.resume.experience;

import com.example.leapit.resume.experience.techstack.ExperienceTechStack;
import com.example.leapit.resume.experience.techstack.ExperienceTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceTechStackRepository experienceTechStackRepository;

    public List<ExperienceResponse.DetailDTO> getDTOsByResumeId(Integer resumeId){
        List<Experience> experienceList = experienceRepository.findAllByResumeId(resumeId);
        List<ExperienceResponse.DetailDTO> dtoList = new ArrayList<>();


        for (Experience experience : experienceList) {
            List<ExperienceTechStack> techStacks = experienceTechStackRepository.findAllExperienceId(experience.getId());
            dtoList.add(new ExperienceResponse.DetailDTO(experience, techStacks));
        }

        return dtoList;
    }


}

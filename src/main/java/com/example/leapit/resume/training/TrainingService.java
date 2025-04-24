package com.example.leapit.resume.training;

import com.example.leapit.resume.training.techstack.TrainingTechStack;
import com.example.leapit.resume.training.techstack.TrainingTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingTechStackRepository trainingTechStackRepository;

    public List<TrainingResponse.DetailDTO> getDTOsByResumeId(Integer resumeId) {
        List<Training> trainingList = trainingRepository.findAllByResumeId(resumeId);
        List<TrainingResponse.DetailDTO> dtoList = new ArrayList<>();

        for (Training training : trainingList) {
            List<TrainingTechStack> techStacks = trainingTechStackRepository.findAllByTrainingId(training.getId());
            dtoList.add(new TrainingResponse.DetailDTO(training, techStacks));
        }
        return dtoList;
    }
}

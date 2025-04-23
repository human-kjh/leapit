package com.example.leapit.resume;

import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.positiontype.PositionTypeService;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.resume.techstack.ResumeTechStack;
import com.example.leapit.resume.techstack.ResumeTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final PositionTypeService positionTypeService;
    private final ResumeTechStackRepository resumeTechStackRepository;

    public List<Resume> list(int userId) {
        // 자신의 userId로 된 모든 resume을 찾아서 return
        return resumeRepository.findAllById(userId);
    }

    public ResumeResponse.DetailDTO detail(int resumeId) {

        Resume resume =  resumeRepository.findByIdJoinUser(resumeId);

        // 직무 : code -> label
        String code = resume.getPositionType();
        String label = positionTypeService.codeToLabel(code);

        List<ResumeTechStack> techStacks  = resumeTechStackRepository.findAllById(resumeId);


        ResumeResponse.DetailDTO detailDTO = new ResumeResponse.DetailDTO(resume, label, techStacks);
        return detailDTO;
    }
}

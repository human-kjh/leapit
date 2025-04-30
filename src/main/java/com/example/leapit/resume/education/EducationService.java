package com.example.leapit.resume.education;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class EducationService {
    private final EducationRepository educationRepository;

    @Transactional
    public void update(Resume resumePS, List<ResumeRequest.UpdateDTO.EducationDTO> educationDTOList) {
        List<Education> educationList = resumePS.getEducations();

        Map<Integer, ResumeRequest.UpdateDTO.EducationDTO> dtoMap = educationDTOList.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.EducationDTO::getId, dto -> dto));

        Iterator<Education> iterator = educationList.iterator();
        while (iterator.hasNext()) {
            Education education = iterator.next();
            ResumeRequest.UpdateDTO.EducationDTO dto = dtoMap.remove(education.getId());

            if (dto != null) {
                education.update(
                        dto.getGraduationDate(),
                        dto.getIsDropout(),
                        dto.getEducationLevel(),
                        dto.getSchoolName(),
                        dto.getMajor(),
                        dto.getGpa(),
                        dto.getGpaScale()
                );
            } else {
                iterator.remove();
            }
        }

        for (ResumeRequest.UpdateDTO.EducationDTO dto : educationDTOList) {
            if (dto.getId() == null) {
                Education newEducation = Education.builder()
                        .resume(resumePS)
                        .graduationDate(dto.getGraduationDate())
                        .isDropout(dto.getIsDropout())
                        .educationLevel(dto.getEducationLevel())
                        .schoolName(dto.getSchoolName())
                        .major(dto.getMajor())
                        .gpa(dto.getGpa())
                        .gpaScale(dto.getGpaScale())
                        .build();
                educationList.add(newEducation);
            }
        }
    }

    public List<EducationResponse.DetailDTO> getDTOsByResumeId(Integer resumeId) {
        List<Education> educationList = educationRepository.findAllByResumeId(resumeId);
        List<EducationResponse.DetailDTO> dtoList = new ArrayList<>();

        for (Education education : educationList) {
            dtoList.add(new EducationResponse.DetailDTO(education));
        }

        return dtoList;
    }
}

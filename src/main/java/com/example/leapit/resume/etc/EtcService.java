package com.example.leapit.resume.etc;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
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
public class EtcService {
    private final EtcRepository etcRepository;

    @Transactional
    public void update(Resume resumePS, List<ResumeRequest.UpdateDTO.EtcDTO> etcDTOList) {
        List<Etc> etcList = resumePS.getEtcs();

        Map<Integer, ResumeRequest.UpdateDTO.EtcDTO> dtoMap = etcDTOList.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.EtcDTO::getId, dto -> dto));

        Iterator<Etc> iterator = etcList.iterator();
        while (iterator.hasNext()) {
            Etc etc = iterator.next();
            ResumeRequest.UpdateDTO.EtcDTO dto = dtoMap.remove(etc.getId());

            if (dto != null) {
                etc.update(
                        dto.getStartDate(),
                        dto.getEndDate(),
                        dto.getHasEndDate(),
                        dto.getTitle(),
                        dto.getEtcType(),
                        dto.getInstitutionName(),
                        dto.getDescription()
                );
            } else {
                iterator.remove();
            }
        }

        for (ResumeRequest.UpdateDTO.EtcDTO dto : etcDTOList) {
            if (dto.getId() == null) {
                Etc newEtc = Etc.builder()
                        .resume(resumePS)
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .hasEndDate(dto.getHasEndDate())
                        .title(dto.getTitle())
                        .etcType(dto.getEtcType())
                        .institutionName(dto.getInstitutionName())
                        .description(dto.getDescription())
                        .build();
                etcList.add(newEtc);
            }
        }
    }

    public List<EtcResponse.DetailDTO> getDTOsByResumeId(Integer resumeId) {
        List<Etc> etcList = etcRepository.findAllByResumeId(resumeId);
        List<EtcResponse.DetailDTO> dtoList = new ArrayList<>();

        for (Etc etc : etcList) {
            dtoList.add(new EtcResponse.DetailDTO(etc));
        }

        return dtoList;
    }
}

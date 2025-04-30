package com.example.leapit.resume.link;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LinkService {
    private final LinkRepository linkRepository;

    @Transactional
    public void update(Resume resumePS, List<ResumeRequest.UpdateDTO.LinkDTO> linkDTOList) {
        List<Link> linkList = resumePS.getLinks();

        Map<Integer, ResumeRequest.UpdateDTO.LinkDTO> dtoMap = linkDTOList.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.LinkDTO::getId, dto -> dto));

        Iterator<Link> iterator = linkList.iterator();
        while (iterator.hasNext()) {
            Link link = iterator.next();
            ResumeRequest.UpdateDTO.LinkDTO dto = dtoMap.remove(link.getId());

            if (dto != null) {
                link.update(dto.getTitle(), dto.getUrl());
            } else {
                iterator.remove();
            }
        }

        for (ResumeRequest.UpdateDTO.LinkDTO dto : linkDTOList) {
            if (dto.getId() == null) {
                Link newLink = Link.builder()
                        .resume(resumePS)
                        .title(dto.getTitle())
                        .url(dto.getUrl())
                        .build();
                linkList.add(newLink);
            }
        }
    }
}

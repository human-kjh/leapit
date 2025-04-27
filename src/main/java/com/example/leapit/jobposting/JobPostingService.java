package com.example.leapit.jobposting;

import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.positiontype.PositionTypeResponse;
import com.example.leapit.common.region.RegionRepository;
import com.example.leapit.common.region.RegionResponse;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.jobposting.techstack.JobPostingTechStackRepository;
import com.example.leapit.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final TechStackRepository techStackRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final JobPostingTechStackRepository jobPostingTechStackRepository;
    private final PositionTypeRepository positionTypeRepository;
    private final RegionRepository regionRepository;

    // 채용 공고 등록
    @org.springframework.transaction.annotation.Transactional
    public void save(JobPostingRequest.SaveDTO saveDTO, User sessionUser, String[] techStacks) {
        JobPosting jobPosting = saveDTO.toEntity(sessionUser);
        jobPostingRepository.save(jobPosting);
        // 2. 선택된 기술 스택 처리
        if (techStacks != null && techStacks.length > 0) {
            for (String techStackCode : techStacks) {
                // 해당 코드의 TechStack 엔티티 조회
                TechStack techStack = techStackRepository.findByCode(techStackCode);
                if (techStack != null) {
                    // JobPostingTechStack 엔티티 생성 및 연관 관계 설정
                    JobPostingTechStack jobPostingTechStack = new JobPostingTechStack(jobPosting, techStack);
                    // JobPostingTechStack 저장
                    jobPostingRepository.saveJobPostingTechStack(jobPostingTechStack);
                }
            }
        }
    }

    // 특정 채용 공고에 등록된 기술 스택 목록 조회
    public List<String> getTechStacksByJobPostingId(Integer jobPostingId) {
        return jobPostingRepository.findTechStacksByJobPostingId(jobPostingId);
    }

    // 채용 공고 삭제
    @org.springframework.transaction.annotation.Transactional
    public void delete(Integer id) {
        jobPostingRepository.deleteById(id);
    }

    // 아이디 조회
    public JobPosting findById(Integer id) {
        return jobPostingRepository.findById(id);
    }

    // 채용 공고 수정
    @Transactional
    public void update(Integer id, JobPostingRequest.UpdateDTO updateDTO, String[] techStacks) {
        JobPosting jobPosting = jobPostingRepository.findById(id);
        jobPosting.update(updateDTO);

        jobPostingTechStackRepository.deleteByJobPostingId(id);

        if (techStacks != null) {
            for (String techStackCode : techStacks) {
                TechStack techStack = techStackRepository.findByCode(techStackCode);
                if (techStack != null) {
                    JobPostingTechStack jobPostingTechStack = new JobPostingTechStack(jobPosting, techStack);
                    jobPostingRepository.saveJobPostingTechStack(jobPostingTechStack);
                }
            }
        }
    }

    // 진행 중인 채용 공고 목록 조회
    public List<JobPosting> OpenJobPostings() {
        LocalDate now = LocalDate.now();
        return jobPostingRepository.findByDeadlineOpen(now);
    }

    // 마감된 채용 공고 목록 조회
    public List<JobPosting> ClosedJobPostings() {
        LocalDate now = LocalDate.now();
        return jobPostingRepository.findByDeadlineClosed(now);
    }


    // TODO 지금하는거 < 김정원
    public JobPostingResponse.JobPostingListFilterDTO 공고목록페이지(Integer regionId, Integer subRegionId, Integer career, String techStackCode, String selectedLabel) {

        // 직무 조회
        List<PositionTypeResponse.PositionTypeDTO> positions = positionTypeRepository.findAllLabel(selectedLabel);
        System.out.println("[DEBUG] 직무 목록 개수: " + positions.size());

        // 🔥 여기서 selected 처리
        for (PositionTypeResponse.PositionTypeDTO position : positions) {
            boolean isSelected = selectedLabel != null && position.getLabel().equals(selectedLabel);
            position.setSelected(isSelected);
        }

        // 기술 스택 조회
        List<TechStack> techStacks = techStackRepository.findAll();

        // 지역 조회
        List<RegionResponse.RegionDTO> regions = regionRepository.findAllRegions();

        // 서브 지역 조회
        List<RegionResponse.SubRegionDTO> subRegions = regionRepository.findAllSubRegions(regionId);
        if (subRegions == null || subRegions.isEmpty()) {
            subRegions = new ArrayList<>();
        }

        // 지역 이름 가져옴
        String selectedSubRegionName = null;
        String selectedRegionName = null;
        String selectedCareerName = null;
        String selectedTechStackName = null;
        boolean hasAnyParam = (regionId != null || subRegionId != null || career != null || techStackCode != null || selectedLabel != null);

        if (techStackCode != null) {
            for (TechStack stack : techStacks) {
                if (stack.getCode().equals(techStackCode)) {
                    selectedTechStackName = stack.getCode();
                }
            }
        }

        if (career != null) {
            if (career == 0) selectedCareerName = "신입";
            else if (career == 1) selectedCareerName = "1년";
            else if (career == 2) selectedCareerName = "2년";
            else if (career == 3) selectedCareerName = "3년";
            else if (career == 9) selectedCareerName = "9년";
            else if (career == 10) selectedCareerName = "10년 이상";
        }

        if (regionId != null) {
            for (RegionResponse.RegionDTO region : regions) {
                if (region.getRegionId().equals(regionId)) {
                    selectedRegionName = region.getRegion();
                }
            }
        }

        if (subRegionId != null) {
            for (RegionResponse.SubRegionDTO subRegion : subRegions) {
                if (subRegion.getSubRegionId().equals(subRegionId)) {
                    selectedSubRegionName = subRegion.getSubRegion();
                }
            }
        }

        // 전체 공고목록 조회
        List<JobPostingResponse.JobPostingDTO> jobPostingList = jobPostingRepository.findAllJobPostingsWithTechStacksByFilter();

        JobPostingResponse.JobPostingListFilterDTO respDTO =
                new JobPostingResponse.JobPostingListFilterDTO(
                        positions,
                        techStacks,
                        regions,
                        subRegions,
                        jobPostingList,
                        regionId,
                        subRegionId,
                        selectedRegionName,
                        selectedSubRegionName,
                        career,
                        selectedCareerName,
                        techStackCode,
                        selectedTechStackName,
                        hasAnyParam,
                        selectedLabel
                );

        return respDTO;
    }


    // TODO
    // 구직자 - 채용공고 목록
    public List<JobPostingResponse.JobPostingDTO> getAllJobPostings() {
        List<Object[]> results = jobPostingRepository.findAllJobPostingsWithTechStacks();

        // 공고 ID별로 묶기
        Map<Integer, JobPosting> postingMap = new HashMap<>();
        Map<Integer, List<JobPostingTechStack>> stackMap = new HashMap<>();

        for (Object[] row : results) {
            JobPosting jp = (JobPosting) row[0];
            JobPostingTechStack stack = (JobPostingTechStack) row[1];

            postingMap.putIfAbsent(jp.getId(), jp);

            if (stack != null) {
                stackMap.computeIfAbsent(jp.getId(), k -> new ArrayList<>()).add(stack);
            }
        }

        // 지역 이름 조회 후 DTO 변환
        List<JobPostingResponse.JobPostingDTO> jobpostingList = new ArrayList<>();
        for (JobPosting jp : postingMap.values()) {
            String region = jobPostingRepository.findByRegion(jp.getId());
            String subRegion = jobPostingRepository.findBySubRegion(jp.getId());

            String address = (region != null ? region : "") + " " + (subRegion != null ? subRegion : "");

            List<JobPostingTechStack> techStacks = stackMap.getOrDefault(jp.getId(), new ArrayList<>());

            CompanyInfo companyInfo = companyInfoRepository.findByUserId(jp.getUser().getId());

            jobpostingList.add(new JobPostingResponse.JobPostingDTO(jp, techStacks, address, companyInfo.getImage(), companyInfo.getCompanyName()));
        }

        return jobpostingList;
    }


    // 구직자 - 메인페이지 최신공고 3개
    public List<JobPostingResponse.MainDTO.MainRecentJobPostingDTO> getRecentPostings() {
        List<JobPosting> recentPostings = jobPostingRepository.findTop3RecentJobPostings();
        AtomicInteger index = new AtomicInteger(0);

        return recentPostings.stream()
                .map(jp -> {
                    int i = index.getAndIncrement();
                    CompanyInfo ci = companyInfoRepository.findByUserId(jp.getUser().getId());
                    return new JobPostingResponse.MainDTO.MainRecentJobPostingDTO(jp, ci, i == 0);
                })
                .collect(Collectors.toList());
    }


    // 구직자 - 메인페이지 인기 공고 8개
    public List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> getPopularJobPostings() {
        List<Object[]> results = jobPostingRepository.findTop8PopularJobPostingsWithTechStacks();

        Map<Integer, JobPosting> postingMap = new HashMap<>();
        Map<Integer, List<JobPostingTechStack>> stackMap = new HashMap<>();

        for (Object[] row : results) {
            JobPosting jp = (JobPosting) row[0];
            JobPostingTechStack stack = (JobPostingTechStack) row[1];

            postingMap.putIfAbsent(jp.getId(), jp);

            if (stack != null) {
                stackMap.computeIfAbsent(jp.getId(), k -> new ArrayList<>()).add(stack);
            }
        }

        List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> popularList = new ArrayList<>();
        for (JobPosting jp : postingMap.values()) {
            String region = jobPostingRepository.findByRegion(jp.getId());
            String subRegion = jobPostingRepository.findBySubRegion(jp.getId());
            String address = (region != null ? region : "") + " " + (subRegion != null ? subRegion : "");

            List<JobPostingTechStack> techStacks = stackMap.getOrDefault(jp.getId(), new ArrayList<>());
            CompanyInfo companyInfo = companyInfoRepository.findByUserId(jp.getUser().getId());

            popularList.add(new JobPostingResponse.MainDTO.MainPopularJobPostingDTO(
                    jp,
                    companyInfo.getCompanyName(),
                    companyInfo.getImage(),
                    address,
                    techStacks
            ));
        }

        return popularList;
    }


}

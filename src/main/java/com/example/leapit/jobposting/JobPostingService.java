package com.example.leapit.jobposting;

import com.example.leapit._core.error.ex.Exception403;
import com.example.leapit._core.error.ex.Exception404;
import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.positiontype.PositionTypeResponse;
import com.example.leapit.common.region.RegionRepository;
import com.example.leapit.common.region.RegionResponse;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.jobposting.bookmark.JobPostingBookmarkRepository;
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
    private final JobPostingBookmarkRepository jobPostingBookmarkRepository;

    // 채용 공고 등록
    @org.springframework.transaction.annotation.Transactional
    public void save(JobPostingRequest.SaveDTO saveDTO, User sessionUser, String[] techStacks) {
        if (sessionUser == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        CompanyInfo companyInfo = companyInfoRepository.findByUserId(sessionUser.getId());
        if (companyInfo == null) {
            throw new Exception403("기업정보가 없어서 채용공고를 등록할 수 없습니다.");
        }

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
    @Transactional
    public void delete(Integer id, Integer sessionUserId) {

        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        JobPosting jobPosting = jobPostingRepository.findById(id);
        if (jobPosting == null) throw new Exception404("채용공고를 찾을 수 없습니다.");

        if (!jobPosting.getUser().getId().equals(sessionUserId)) throw new Exception403("권한이 없습니다.");

        jobPostingRepository.deleteById(id);
    }

    // 아이디 조회
    public JobPosting findById(Integer id) {
        return jobPostingRepository.findById(id);
    }

    // 채용 공고 수정
    @Transactional
    public void update(Integer id, JobPostingRequest.UpdateDTO updateDTO, String[] techStacks, Integer sessionUserId) {
        if (sessionUserId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        JobPosting jobPosting = jobPostingRepository.findById(id);
        if (jobPosting == null) throw new Exception404("채용공고를 찾을 수 없습니다.");

        if (!jobPosting.getUser().getId().equals(sessionUserId)) throw new Exception403("권한이 없습니다.");

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
    public List<JobPosting> OpenJobPostings(Integer userId) {
        if (userId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        LocalDate now = LocalDate.now();
        return jobPostingRepository.findByDeadlineOpen(now, userId);
    }

    // 마감된 채용 공고 목록 조회
    public List<JobPosting> ClosedJobPostings(Integer userId) {
        if (userId == null) throw new Exception404("회원정보가 존재하지 않습니다.");

        LocalDate now = LocalDate.now();
        return jobPostingRepository.findByDeadlineClosed(now, userId);
    }


    public JobPostingResponse.JobPostingListFilterDTO 공고목록페이지(
            Integer regionId,
            Integer subRegionId,
            Integer career,
            String techStackCode,
            String selectedLabel,
            Boolean isPopular,
            Boolean isLatest,
            Integer sessionUserId) {

        // 직무 조회
        List<PositionTypeResponse.PositionTypeDTO> positions = positionTypeRepository.findAllLabelAndSelectedLabel(selectedLabel);


        for (PositionTypeResponse.PositionTypeDTO position : positions) {
            boolean isSelected = selectedLabel != null && position.getCode().equals(selectedLabel);
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
                    selectedTechStackName = stack.getCode(); // (네 목표는 코드 넣기)
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
        List<JobPostingResponse.JobPostingDTO> jobPostingList = jobPostingRepository.findAllJobPostingsWithTechStacksByFilter(
                regionId,
                subRegionId,
                career,
                techStackCode,
                selectedLabel,
                Boolean.TRUE.equals(isPopular),
                Boolean.TRUE.equals(isLatest),
                sessionUserId
        );


        // 북마크 조회
//        JobPostingBookmark bookmark = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(sessionUserId, jobPostingId);


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
                        selectedLabel,
                        Boolean.TRUE.equals(isPopular),
                        Boolean.TRUE.equals(isLatest)
                );

        return respDTO;
    }


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

    // JobPosting의 주소 정보를 반환하는 메서드
    public JobPostingResponse.AddressDTO getJobPostingAddress(Integer id) {
        return jobPostingRepository.findJobPostingAddressById(id);
    }


    // 구직자 - 메인페이지 최신공고 3개
    public List<JobPostingResponse.MainDTO.MainRecentJobPostingDTO> getRecentPostings(Integer userId) {
        List<JobPosting> recentPostings = jobPostingRepository.findTop3RecentJobPostings();
        AtomicInteger index = new AtomicInteger(0);


        return recentPostings.stream()
                .map(jp -> {
                    int i = index.getAndIncrement();
                    CompanyInfo ci = companyInfoRepository.findByUserId(jp.getUser().getId());
                    boolean isBookmarked = false;
                    if (userId != null) {
                        isBookmarked = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(userId, jp.getId()) != null;
                    }
                    return new JobPostingResponse.MainDTO.MainRecentJobPostingDTO(jp, ci, i == 0, isBookmarked);
                })
                .collect(Collectors.toList());
    }


    // 구직자 - 메인페이지 인기 공고 8개
    public List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> getPopularJobPostings(Integer userId) {
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
        List<Integer> top8Ids = jobPostingRepository.top8();
        for (Integer id : top8Ids) {
            JobPosting jp = postingMap.get(id);
            if (jp == null) continue; // 혹시라도 누락될 경우 안전 처리

            String region = jobPostingRepository.findByRegion(jp.getId());
            String subRegion = jobPostingRepository.findBySubRegion(jp.getId());
            String address = (region != null ? region : "") + " " + (subRegion != null ? subRegion : "");

            List<JobPostingTechStack> techStacks = stackMap.getOrDefault(jp.getId(), new ArrayList<>());
            CompanyInfo companyInfo = companyInfoRepository.findByUserId(jp.getUser().getId());

            boolean isBookmarked = false;
            if (userId != null) {
                isBookmarked = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(userId, jp.getId()) != null;
            }
            popularList.add(new JobPostingResponse.MainDTO.MainPopularJobPostingDTO(
                    jp,
                    companyInfo.getCompanyName(),
                    companyInfo.getImage(),
                    address,
                    techStacks,
                    isBookmarked
            ));
        }

        return popularList;
    }


    @Transactional
    public void increaseViewCount(Integer jobPostingId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId);
        if (jobPosting == null) throw new Exception404("채용공고를 찾을 수 없습니다.");
        jobPostingRepository.findById(jobPostingId);
        jobPosting.setViewCount(jobPosting.getViewCount() + 1);
    }
}

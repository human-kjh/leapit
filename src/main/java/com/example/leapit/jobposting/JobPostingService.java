package com.example.leapit.jobposting;

import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final CompanyInfoRepository companyInfoRepository;

    @Transactional
    public void createJobPosting(JobPostingRequest request, User user) {
        JobPosting jobPosting = request.toEntity(user); // 빌더로 생성
        jobPostingRepository.save(jobPosting);
    }

    @Transactional
    public void deleteJobPosting(Integer id) {
        jobPostingRepository.deleteById(id);
    }

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
}
package com.example.leapit.jobposting;

import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final TechStackRepository techStackRepository;

    // 채용 공고 등록
    @Transactional
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
    @Transactional
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

        jobPostingRepository.deleteJobPostingTechStacksByJobPostingId(id);

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
}

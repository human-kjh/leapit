package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    // 채용 공고 등록
    @Transactional
    public void save(JobPostingRequest.SaveDTO saveDTO, User sessionUser) {
        JobPosting jobPosting = saveDTO.toEntity(sessionUser);
        jobPostingRepository.save(jobPosting);
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
    public void update(Integer id, JobPostingRequest.UpdateDTO updateDTO) {
        JobPosting jobPosting = jobPostingRepository.findById(id);

        if (jobPosting != null) {
            updateDTO.toEntity(jobPosting);
        } else {
            throw new RuntimeException("채용 공고를 찾을 수 없습니다.");
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

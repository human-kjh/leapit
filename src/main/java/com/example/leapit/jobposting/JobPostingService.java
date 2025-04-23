package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public void createJobPosting(JobPostingRequest request, User user) {
        JobPosting jobPosting = request.toEntity(user); // 빌더로 생성
        jobPostingRepository.save(jobPosting);
    }

    @Transactional
    public void deleteJobPosting(Integer id) {
        jobPostingRepository.deleteById(id);
    }

    public JobPosting findById(Integer id) {
        return jobPostingRepository.findById(id);
    }

    @Transactional
    public void updateJobPosting(Integer id, JobPostingRequest request) {
        JobPosting existingJobPosting = jobPostingRepository.findById(id);

        // null 체크 (매우 중요!)
        if (existingJobPosting != null) {
            existingJobPosting.setTitle(request.getTitle());
            existingJobPosting.setPositionType(request.getPositionType());
            existingJobPosting.setMinCareerLevel(request.getMinCareerLevel());
            existingJobPosting.setMaxCareerLevel(request.getMaxCareerLevel());
            existingJobPosting.setEducationLevel(request.getEducationLevel());
            existingJobPosting.setAddressRegionId(request.getAddressRegionId());
            existingJobPosting.setAddressSubRegionId(request.getAddressSubRegionId());
            existingJobPosting.setAddressDetail(request.getAddressDetail());
            existingJobPosting.setServiceIntro(request.getServiceIntro());
            existingJobPosting.setDeadline(request.getDeadline());
            existingJobPosting.setResponsibility(request.getResponsibility());
            existingJobPosting.setQualification(request.getQualification());
            existingJobPosting.setPreference(request.getPreference());
            existingJobPosting.setBenefit(request.getBenefit());
            existingJobPosting.setAdditionalInfo(request.getAdditionalInfo());
            existingJobPosting.setTechStack(request.getTechStack());

            jobPostingRepository.update(existingJobPosting);

        }
        // 찾지 못하면 아무것도 하지 않음 TODO
    }
}
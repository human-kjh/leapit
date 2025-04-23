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

    @Transactional
    public void createJobPosting(JobPostingRequest.SaveDTO saveDTO, User user) {
        // SaveDTO에 담긴 정보를 JobPosting 엔티티로 변환
        JobPosting jobPosting = saveDTO.toEntity(user);
        // 변환된 JobPosting 엔티티를 데이터베이스에 저장
        jobPostingRepository.save(jobPosting);
    }

    @Transactional
    public void deleteJobPosting(Integer id) {
        // 주어진 ID에 해당하는 채용 공고를 데이터베이스에서 삭제
        jobPostingRepository.deleteById(id);
    }

    // 특정 ID의 채용 공고 정보를 조회하는 메서드
    public JobPosting findById(Integer id) {
        // 주어진 ID로 데이터베이스에서 채용 공고 정보를 찾아 반환
        return jobPostingRepository.findById(id);
    }

    @Transactional
    public void updateJobPosting(Integer id, JobPostingRequest.UpdateDTO updateDTO) {
        // 주어진 ID에 해당하는 기존 채용 공고 정보를 데이터베이스에서 조회
        JobPosting existingJobPosting = jobPostingRepository.findById(id);

        // 조회된 기존 채용 공고가 존재하는 경우에만 업데이트 수행
        if (existingJobPosting != null) {
            // UpdateDTO에 값이 있는 필드만 기존 채용 공고의 해당 필드 값을 업데이트
            if (updateDTO.getTitle() != null) {
                existingJobPosting.setTitle(updateDTO.getTitle());
            }
            if (updateDTO.getPositionType() != null) {
                existingJobPosting.setPositionType(updateDTO.getPositionType());
            }
            if (updateDTO.getMinCareerLevel() != null) {
                existingJobPosting.setMinCareerLevel(updateDTO.getMinCareerLevel());
            }
            if (updateDTO.getMaxCareerLevel() != null) {
                existingJobPosting.setMaxCareerLevel(updateDTO.getMaxCareerLevel());
            }
            if (updateDTO.getEducationLevel() != null) {
                existingJobPosting.setEducationLevel(updateDTO.getEducationLevel());
            }
            if (updateDTO.getAddressRegionId() != null) {
                existingJobPosting.setAddressRegionId(updateDTO.getAddressRegionId());
            }
            if (updateDTO.getAddressSubRegionId() != null) {
                existingJobPosting.setAddressSubRegionId(updateDTO.getAddressSubRegionId());
            }
            if (updateDTO.getAddressDetail() != null) {
                existingJobPosting.setAddressDetail(updateDTO.getAddressDetail());
            }
            if (updateDTO.getServiceIntro() != null) {
                existingJobPosting.setServiceIntro(updateDTO.getServiceIntro());
            }
            if (updateDTO.getDeadline() != null) {
                existingJobPosting.setDeadline(updateDTO.getDeadline());
            }
            if (updateDTO.getResponsibility() != null) {
                existingJobPosting.setResponsibility(updateDTO.getResponsibility());
            }
            if (updateDTO.getQualification() != null) {
                existingJobPosting.setQualification(updateDTO.getQualification());
            }
            if (updateDTO.getPreference() != null) {
                existingJobPosting.setPreference(updateDTO.getPreference());
            }
            if (updateDTO.getBenefit() != null) {
                existingJobPosting.setBenefit(updateDTO.getBenefit());
            }
            if (updateDTO.getAdditionalInfo() != null) {
                existingJobPosting.setAdditionalInfo(updateDTO.getAdditionalInfo());
            }
            if (updateDTO.getTechStack() != null) {
                existingJobPosting.setTechStack(updateDTO.getTechStack());
            }

            // 변경된 기존 채용 공고 정보를 데이터베이스에 업데이트
            jobPostingRepository.update(existingJobPosting);

        }
        // 찾지 못하면 아무것도 하지 않음 TODO: 예외 처리 필요
    }

    // 진행 중인 채용 공고 목록을 조회하는 메서드
    public List<JobPosting> getOngoingJobPostings() {
        // 현재 날짜를 기준으로 마감일이 같거나 이후인 채용 공고 목록을 데이터베이스에서 조회
        LocalDate now = LocalDate.now();
        return jobPostingRepository.findByDeadlineGreaterThanEqual(now);
    }

    // 마감된 채용 공고 목록을 조회하는 메서드
    public List<JobPosting> getClosedJobPostings() {
        // 현재 날짜를 기준으로 마감일 이전인 채용 공고 목록을 데이터베이스에서 조회
        LocalDate now = LocalDate.now();
        return jobPostingRepository.findByDeadlineBefore(now);
    }
}
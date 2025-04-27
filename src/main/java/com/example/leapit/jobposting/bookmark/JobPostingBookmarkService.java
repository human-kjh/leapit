package com.example.leapit.jobposting.bookmark;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.JobPostingRepository;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostingBookmarkService {
    private final JobPostingBookmarkRepository jobPostingBookmarkRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    
    @Transactional
    public JobPostingBookmarkResponse.SaveDTO saveJobPostingBookmarkByUserId(JobPostingBookmarkRequest.SaveDTO reqDTO, Integer sessionUserId) {

        User personalUser = userRepository.findById(sessionUserId);

        if (personalUser == null) {
            throw new RuntimeException("유저가 존재하지 않습니다");
        }

        JobPosting jobPosting = jobPostingRepository.findByJobPostingId(reqDTO.getJobPostingId());
        if (jobPosting == null) {
            throw new RuntimeException("지원 정보가 존재하지 않습니다");
        }

        JobPostingBookmark bookmark = JobPostingBookmark.builder()
                .user(personalUser)
                .jobPosting(jobPosting)
                .build();

        JobPostingBookmark savedBookmark = jobPostingBookmarkRepository.save(bookmark);

        return new JobPostingBookmarkResponse.SaveDTO(bookmark.getId());
    }

    @Transactional
    public void deleteJobPostingBookmarkByBookmarkId(Integer jobPostingId, Integer sessionUserId) {
        // 북마크 조회
        JobPostingBookmark bookmark = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(sessionUserId, jobPostingId);

        // 북마크가 존재하는지 확인
        if (bookmark == null) {
            throw new RuntimeException("해당 스크랩이 존재하지 않습니다.");
        }

        // 권한 확인
        if (!bookmark.getUser().getId().equals(sessionUserId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 북마크 삭제
        jobPostingBookmarkRepository.delete(bookmark);
    }
}

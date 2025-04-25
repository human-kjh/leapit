package com.example.leapit.jobposting.bookmark;

import com.example.leapit.application.Application;
import com.example.leapit.application.bookmark.ApplicationBookmark;
import com.example.leapit.application.bookmark.ApplicationBookmarkRequest;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
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
    public JobPostingBookmarkResponse.SaveDTO saveJobPostingBookmarkByUserId(ApplicationBookmarkRequest.SaveDTO reqDTO, Integer sessionUserId) {

        User companyUser = userRepository.findById(sessionUserId);
        if (companyUser == null) throw new RuntimeException("유저가 존재하지 않습니다");

        JobPosting jobPosting = jobPostingRepository.findByApplicationId(reqDTO.getApplicationId());
        if (jobPosting == null) throw new RuntimeException("지원 정보가 존재하지 않습니다");

        JobPostingBookmark bookmark = JobPostingBookmark.builder()
                .user(companyUser)
                .jobPosting(jobPosting)
                .build();

        jobPostingBookmarkRepository.save(bookmark);

        return new JobPostingBookmarkResponse.SaveDTO(bookmark.getId());
    }

    @Transactional
    public void deleteJobPostingBookmarkByBookmarkId(Integer jobPostingId, Integer sessionUserId) {

        JobPostingBookmark bookmark = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(sessionUserId, jobPostingId);

        if (bookmark == null) throw new RuntimeException("해당 스크랩이 존재하지 않습니다.");
        if (!bookmark.getUser().getId().equals(sessionUserId)) throw new RuntimeException("권한이 없습니다.");

        jobPostingBookmarkRepository.delete(bookmark);
    }
}

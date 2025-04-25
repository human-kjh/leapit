package com.example.leapit.jobposting.bookmark;

import com.example.leapit._core.util.Resp;
import com.example.leapit.application.bookmark.ApplicationBookmarkRequest;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class JobPostingBookmarkController {
    private final JobPostingBookmarkService jobPostingBookmarkService;
    private final HttpSession session;

    // 개인 스크랩 등록 job_posting_bookmark
    @PostMapping("/api/personal/bookmark")
    public Resp<?> saveBookmark(@RequestBody ApplicationBookmarkRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobPostingBookmarkResponse.SaveDTO respDTO = jobPostingBookmarkService.saveJobPostingBookmarkByUserId(reqDTO, sessionUser.getId());

        return Resp.ok(respDTO);
    }

    // 개인 스크랩 삭제 job_posting_bookmark
    @DeleteMapping("/api/personal/bookmark/{id}")
    public Resp<?> deleteBookmark(@PathVariable("id") Integer jobPostingId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("applicationId = " + jobPostingId + ", sessionUserId = " + sessionUser.getId());

        jobPostingBookmarkService.deleteJobPostingBookmarkByBookmarkId(jobPostingId, sessionUser.getId());
        return Resp.ok("북마크 삭제");
    }
}

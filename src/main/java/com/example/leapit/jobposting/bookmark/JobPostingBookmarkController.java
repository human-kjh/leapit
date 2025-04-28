package com.example.leapit.jobposting.bookmark;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import com.example.leapit._core.util.Resp;
import com.example.leapit.application.bookmark.ApplicationBookmarkRequest;
import com.example.leapit.application.bookmark.ApplicationBookmarkResponse;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
public class JobPostingBookmarkController {
    private final JobPostingBookmarkService jobPostingBookmarkService;
    private final HttpSession session;

    @PostMapping("/api/personal/bookmark")
    public Resp<?> saveBookmark(@RequestBody JobPostingBookmarkRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return Resp.fail(400, "사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
        }

        try {
            JobPostingBookmarkResponse.SaveDTO respDTO = jobPostingBookmarkService.saveJobPostingBookmarkByUserId(reqDTO, sessionUser.getId());
            return Resp.ok(respDTO);
        } catch (Exception e) {
            return Resp.fail(500, "북마크 저장 실패: " + e.getMessage());
        }
    }

    // 개인 스크랩 삭제 job_posting_bookmark
    @DeleteMapping("/api/personal/bookmark/{id}")
    public Resp<?> deleteBookmark(@PathVariable("id") Integer jobPostingId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            return Resp.fail(400, "사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
        }

        try {
            jobPostingBookmarkService.deleteJobPostingBookmarkByBookmarkId(jobPostingId, sessionUser.getId());
            return Resp.ok("북마크 삭제");
        } catch (Exception e) {
            return Resp.fail(500, "북마크 삭제 실패");
        }
    }
}

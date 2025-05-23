package com.example.leapit.jobposting.bookmark;

import com.example.leapit._core.error.ex.ExceptionApi401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JobPostingBookmarkController {
    private final JobPostingBookmarkService jobPostingBookmarkService;
    private final HttpSession session;

    @PostMapping("/s/api/personal/bookmark")
    public Resp<?> saveBookmark(@Valid @RequestBody JobPostingBookmarkRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new ExceptionApi401("사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
        }

        try {
            JobPostingBookmarkResponse.SaveDTO respDTO = jobPostingBookmarkService.saveJobPostingBookmarkByUserId(reqDTO, sessionUser.getId());
            return Resp.ok(respDTO);
        } catch (Exception e) {
            return Resp.fail(500, "북마크 저장 실패: " + e.getMessage());
        }
    }

    // 개인 스크랩 삭제 job_posting_bookmark
    @DeleteMapping("/s/api/personal/bookmark/{id}")
    public Resp<?> deleteBookmark(@PathVariable("id") Integer jobPostingId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new ExceptionApi401("사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
        }

        try {
            jobPostingBookmarkService.deleteJobPostingBookmarkByBookmarkId(jobPostingId, sessionUser.getId());
            return Resp.ok("북마크 삭제");
        } catch (Exception e) {
            return Resp.fail(500, "북마크 삭제 실패");
        }
    }
}

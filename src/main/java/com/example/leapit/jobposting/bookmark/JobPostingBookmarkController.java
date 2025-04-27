package com.example.leapit.jobposting.bookmark;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JobPostingBookmarkController {
    private final JobPostingBookmarkService jobPostingBookmarkService;
    private final HttpSession session;

    @PostMapping("/api/personal/bookmark")
    public Resp<?> saveBookmark(@RequestBody JobPostingBookmarkRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 세션에 사용자 정보가 없으면 오류 처리
        if (sessionUser == null) {
            return Resp.fail(400, "사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
        }

        // 북마크 저장 서비스 호출
        try {
            JobPostingBookmarkResponse.SaveDTO respDTO = jobPostingBookmarkService.saveJobPostingBookmarkByUserId(reqDTO, sessionUser.getId());
            return Resp.ok(respDTO);  // 성공적으로 저장된 경우
        } catch (Exception e) {
            // 오류 발생 시 error 응답 반환
            return Resp.fail(500, "북마크 저장 실패: " + e.getMessage());
        }
    }

    // 개인 스크랩 삭제 job_posting_bookmark
    @DeleteMapping("/api/personal/bookmark/{id}")
    public Resp<?> deleteBookmark(@PathVariable("id") Integer jobPostingId) {
        // 세션에서 로그인한 사용자 정보 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 로그인되지 않은 경우 에러 메시지 반환
        if (sessionUser == null) {
            return Resp.fail(400, "사용자 정보가 없습니다. 로그인 후 다시 시도해주세요.");
        }

        try {
            // 북마크 삭제 서비스 호출
            jobPostingBookmarkService.deleteJobPostingBookmarkByBookmarkId(jobPostingId, sessionUser.getId());
            return Resp.ok("북마크 삭제");
        } catch (Exception e) {
            // 예외 발생 시 로그 찍고 실패 메시지 반환
            System.out.println("삭제 중 오류 발생: " + e.getMessage());
            return Resp.fail(500, "북마크 삭제 실패");
        }
    }
}

package com.example.leapit.application.bookmark;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApplicationBookmarkController {

    private final HttpSession session;
    private final ApplicationBookmarkService bookmarkService;


    // 기업 스크랩 등록 application_bookmark
    @PostMapping("/s/api/company/bookmark")
    public Resp<?> saveApplicationBookmark(@RequestBody ApplicationBookmarkRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        try {
            ApplicationBookmarkResponse.SaveDTO respDTO = bookmarkService.saveApplicantBookmarkByUserId(reqDTO, sessionUser.getId());
            return Resp.ok(respDTO);
        } catch (RuntimeException e) {
            return Resp.fail(400, e.getMessage());
        }
    }

    // 기업 스크랩 삭제 application_bookmark
    @DeleteMapping("/s/api/company/bookmark/{id}")
    public Resp<?> deleteApplicationBookmark(@PathVariable("id") Integer applicationId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        bookmarkService.deleteApplicationBookmarkByApplicationId(applicationId, sessionUser.getId());
        return Resp.ok("북마크 삭제");
    }
}

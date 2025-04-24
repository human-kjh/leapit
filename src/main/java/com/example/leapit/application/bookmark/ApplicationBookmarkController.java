package com.example.leapit.application.bookmark;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApplicationBookmarkController {

    private final HttpSession session;
    private final ApplicationBookmarkService bookmarkService;

    // 스크랩 등록
    @PostMapping("/api/bookmark")
    public Resp<?> saveBookmark(@RequestBody ApplicationBookmarkRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ApplicationBookmarkResponse.SaveDTO respDTO = bookmarkService.스크랩등록(reqDTO, sessionUser.getId());

        return Resp.ok(respDTO);
    }

    // 스크랩 삭제
    @DeleteMapping("/api/bookmark/{id}")
    public Resp<?> deleteBookmark(@PathVariable("id") Integer applicationId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("applicationId = " + applicationId + ", sessionUserId = " + sessionUser.getId());

        bookmarkService.북마크삭제(applicationId, sessionUser.getId());
        return Resp.ok("북마크 삭제");
    }
}


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


    // 기업 스크랩 등록 application_bookmark
    @PostMapping("/api/company/bookmark")
    public Resp<?> saveApplicationBookmark(@RequestBody ApplicationBookmarkRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ApplicationBookmarkResponse.SaveDTO respDTO = bookmarkService.saveApplicantBookmarkByUserId(reqDTO, sessionUser.getId());
        return Resp.ok(respDTO);
    }
    
    // 기업 스크랩 삭제 application_bookmark
    @DeleteMapping("/api/company/bookmark/{id}")
    public Resp<?> deleteApplicationBookmark(@PathVariable("id") Integer applicationId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        bookmarkService.deleteApplicationBookmarkByApplicationId(applicationId, sessionUser.getId());
        return Resp.ok("북마크 삭제");
    }
}


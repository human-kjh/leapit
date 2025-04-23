package com.example.leapit.application;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    // 지원자 현황 관리
    @GetMapping("/personal/mypage/application")
    public String application(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        // 지원 현황 통계
        ApplicationResponse.ApplicationSummaryDto summary = applicationService.getSummaryByUserId(sessionUser.getId());

        // 지원 목록
        List<ApplicationResponse.ApplicationDto> applications = applicationService.getApplicationsByUserId(sessionUser.getId());

        request.setAttribute("model", summary);
        request.setAttribute("models", applications);

        return "personal/mypage/application";
    }
}

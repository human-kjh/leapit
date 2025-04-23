package com.example.leapit.application;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    @GetMapping("/personal/mypage/application")
    public String application(Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        ApplicationResponse.ApplicationSummaryDto summary = applicationService.getSummaryByUserId(sessionUser.getId());
        List<ApplicationResponse.ApplicationDto> applications = applicationService.getApplicationsByUserId(sessionUser.getId());

        model.addAttribute("model", summary);
        model.addAttribute("models", applications);

        return "personal/mypage/application";
    }
}

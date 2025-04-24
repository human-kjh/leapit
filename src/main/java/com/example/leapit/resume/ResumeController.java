package com.example.leapit.resume;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ResumeController {
    private final ResumeService resumeService;
    private final HttpSession session;

    @GetMapping("/resume")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        List<Resume> resumeList = resumeService.list(sessionUser.getId());
        request.setAttribute("models", resumeList);
        return "personal/resume/list";
    }

    @GetMapping("/resume/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(id, sessionUser.getId());
        request.setAttribute("model", detailDTO);
        return "personal/resume/detail";
    }
}

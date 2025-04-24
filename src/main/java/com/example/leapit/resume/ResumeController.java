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

    @GetMapping("/resume/list")
    public String resumeList(HttpServletRequest request) {
        // session 구현 전
        // 로그인 한 유저의 이력서들만 출력된다.
        Integer userId = 2;

        List<Resume> resumeList = resumeService.list(userId);
        request.setAttribute("models", resumeList);
        return "personal/resume/list";
    }

    @GetMapping("/resume/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        ResumeResponse.DetailDTO detailDTO = resumeService.detail(id);
        request.setAttribute("model", detailDTO);
        return "personal/resume/detail";
    }
}

package com.example.leapit.resume;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ResumeController {
    private final ResumeService resumeService;
    private final HttpSession session;

    @GetMapping("/s/personal/resume")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");
        Integer sessionUserId = 1;
        List<Resume> resumeList = resumeService.list(1); // TODO : sessionUser.getId() 인수 추가
        request.setAttribute("models", resumeList);
        return "personal/resume/list";
    }

    @GetMapping("/s/personal/resume/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(id); // TODO : sessionUser.getId() 인수 추가
        request.setAttribute("model", detailDTO);
        return "personal/resume/detail";
    }

    @PostMapping("/s/personal/resume/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        resumeService.delete(id); // TODO : sessionUser.getId() 인수 추가
        return "redirect:/s/personal/resume";
    }

    @GetMapping("/s/personal/resume/save-form")
    public String saveForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");
        ResumeResponse.SaveDTO saveDTO = resumeService.getSaveForm(sessionUser.getId());
        request.setAttribute("model", saveDTO);

        return "personal/resume/save-form";
    }

    @PostMapping("/s/api/personal/resume/save")
    @ResponseBody
    public Resp<?> save(@RequestBody ResumeRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        resumeService.save(saveDTO, sessionUser);
        return Resp.ok(null);
    }
}

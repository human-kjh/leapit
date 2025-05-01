package com.example.leapit.resume;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit._core.error.ex.ExceptionApi401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        List<ResumeResponse.ListDTO> resumeList = resumeService.list(sessionUser.getId());
        request.setAttribute("models", resumeList);
        return "personal/resume/list";
    }

    @GetMapping("/s/personal/resume/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(id, sessionUser, null);
        request.setAttribute("model", detailDTO);
        return "personal/resume/detail";
    }

    @PostMapping("/s/personal/resume/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        resumeService.delete(id, sessionUser.getId());
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

    @PostMapping("/s/api/personal/resume")
    @ResponseBody
    public Resp<?> save(@Valid @RequestPart("dto") ResumeRequest.SaveDTO saveDTO, Errors errors,
                        @RequestPart(value = "photoFile", required = false) MultipartFile photoFile) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        resumeService.save(saveDTO, photoFile, sessionUser);
        return Resp.ok(null);
    }

    @GetMapping("/s/personal/resume/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        ResumeResponse.UpdateDTO updateDTO = resumeService.getUpdateForm(id, sessionUser.getId());
        request.setAttribute("model", updateDTO);
        return "personal/resume/update-form";
    }

    @PutMapping("/s/personal/resume/{id}")
    @ResponseBody
    public Resp<?> update(@PathVariable("id") int id, @Valid @RequestPart("dto") ResumeRequest.UpdateDTO updateDTO, Errors errors,
                          @RequestPart(value = "photoFile", required = false) MultipartFile photoFile) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        resumeService.update(id, sessionUser.getId(), updateDTO, photoFile);
        return Resp.ok(null);
    }
}

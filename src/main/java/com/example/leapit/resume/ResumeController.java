package com.example.leapit.resume;

import com.example.leapit._core.util.Resp;
import com.example.leapit.common.positiontype.PositionType;
import com.example.leapit.common.positiontype.PositionTypeService;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackService;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.leapit._core.util.Resp.ok;

@RequiredArgsConstructor
@Controller
public class ResumeController {
    private final ResumeService resumeService;
    private final HttpSession session;
    private final TechStackService techStackService;
    private final PositionTypeService positionTypeService;

    @GetMapping("/resume")
    public String list(HttpServletRequest request) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");
        Integer sessionUserId = 1;
        List<Resume> resumeList = resumeService.list(1); // TODO : sessionUser.getId() 인수 추가
        request.setAttribute("models", resumeList);
        return "personal/resume/list";
    }

    @GetMapping("/resume/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest request) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(id); // TODO : sessionUser.getId() 인수 추가
        request.setAttribute("model", detailDTO);
        return "personal/resume/detail";
    }

    @PostMapping("/resume/{id}/delete")
    public String delete(@PathVariable("id") int id) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        resumeService.delete(id); // TODO : sessionUser.getId() 인수 추가
        return "redirect:/resume";
    }

    @GetMapping("/resume/save-form")
    public String saveForm(HttpServletRequest request) {

        List<PositionType> positionTypeList = positionTypeService.list();
        List<TechStack> techStackList = techStackService.list();

        request.setAttribute("positionTypeList", positionTypeList);
        request.setAttribute("techStackList", techStackList);

        return "personal/resume/save-form";
    }

    @PostMapping("/resume/save")
    @ResponseBody
    public Resp<?> save(@RequestBody ResumeRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        resumeService.save(saveDTO, sessionUser);
        return Resp.ok(null);
    }
}

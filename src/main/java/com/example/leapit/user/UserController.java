package com.example.leapit.user;

import com.example.leapit._core.error.ex.Exception400;
import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.common.enums.Role;
import com.example.leapit.companyinfo.CompanyInfoService;
import com.example.leapit.jobposting.JobPostingResponse;
import com.example.leapit.jobposting.JobPostingService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;
    private final CompanyInfoService companyInfoService;
    private final JobPostingService jobPostingService;


    @GetMapping("/s/company/user/update-form")
    public String companyUpdateForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        return "company/user/update-form";
    }

    @PostMapping("/s/company/user/update")
    public String update(@Valid UserRequest.CompanyUpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");
        if (!reqDTO.getNewPassword().equals(reqDTO.getConfirmPassword())) throw new Exception400("입력한 비밀번호가 다릅니다.");
        User userPS = userService.update(reqDTO, sessionUser.getId());
        session.setAttribute("sessionUser", userPS);
        return "redirect:/login-form";
    }

    @GetMapping("/s/personal/user/update-form")
    public String personalUpdateForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        return "personal/user/update-form";
    }

    @PostMapping("/s/personal/user/update")
    public String update(@Valid UserRequest.PersonalUpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");
        if (!reqDTO.getNewPassword().equals(reqDTO.getConfirmPassword())) throw new Exception400("입력한 비밀번호가 다릅니다.");

        User userPS = userService.update(reqDTO, sessionUser.getId());
        session.setAttribute("sessionUser", userPS);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO loginDTO, HttpServletResponse response, Errors errors, @RequestParam(required = false) String redirect) {

        User sessionUser = userService.login(loginDTO);
        session.setAttribute("sessionUser", sessionUser);

        if (loginDTO.getRememberMe() == null) {
            Cookie cookie = new Cookie("username", null);
            cookie.setMaxAge(0); // 즉시 만료
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("username", loginDTO.getUsername());
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }

        // 1. redirect 파라미터가 있으면 우선 처리
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }

        // 사용자 역할에 따라 리다이렉트 분기
        if (loginDTO.getRole() == Role.personal) {
            return "redirect:/";
        } else {
            // 회사 사용자일 경우 companyInfoId 확인
            Integer companyInfoId = companyInfoService.findCompanyInfoIdByUserId(sessionUser.getId());

            if (companyInfoId != null) {
                session.setAttribute("companyInfoId", companyInfoId);
                return "redirect:/s/company/info/" + companyInfoId;
            } else {
                session.removeAttribute("companyInfoId");
                return "redirect:/s/company/info/save-form";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-form";
    }

    @GetMapping("/s/company/main")
    public String companyMain() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        return "company/main";
    }


    @GetMapping("/api/check-username-available/{username}")
    public @ResponseBody Resp<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> dto = userService.checkUsernameDuplicate(username);
        return Resp.ok(dto);
    }

    @GetMapping("/personal/user/join-form")
    public String userJoinForm() {
        return "personal/user/join-form";
    }

    @PostMapping("/personal/user/join")
    public String userJoin(@Valid UserRequest.PersonalJoinDTO reqDTO, Errors errors) {
        userService.join(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/company/user/join-form")
    public String companyJoinForm() {
        return "company/user/join-form";
    }

    @PostMapping("/company/user/join")
    public String companyJoin(@Valid UserRequest.CompanyJoinDTO reqDTO) {
        userService.join(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer userId = sessionUser != null ? sessionUser.getId() : null;

        List<JobPostingResponse.MainDTO.MainRecentJobPostingDTO> recent = jobPostingService.getRecentPostings(userId);
        List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> popular = jobPostingService.getPopularJobPostings(userId);

        JobPostingResponse.MainDTO mainDTO = new JobPostingResponse.MainDTO(recent, popular);

        request.setAttribute("model", mainDTO);
        request.setAttribute("isLoggedIn", sessionUser != null);
        return "personal/main/logout";
    }
}
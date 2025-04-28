package com.example.leapit.user;

import com.example.leapit._core.util.Resp;
import com.example.leapit.common.enums.Role;
import com.example.leapit.companyinfo.CompanyInfoService;
import com.example.leapit.jobposting.JobPostingResponse;
import com.example.leapit.jobposting.JobPostingService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/company/user/update-form")
    public String companyUpdateForm() {
        return "company/user/update-form";
    }

    @PostMapping("/company/user/update")
    public String update(UserRequest.CompanyUpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");
        if (!reqDTO.getNewPassword().equals(reqDTO.getConfirmPassword())) throw new RuntimeException("입력한 비밀번호가 다릅니다.");
        if (!reqDTO.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$"))
            throw new RuntimeException("비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        if (!reqDTO.getContactNumber().matches("^010-\\d{4}-\\d{4}$")) {
            throw new RuntimeException("전화번호는 010-1234-5678 형식으로 입력해주세요.");
        }
        User userPS = userService.update(reqDTO, sessionUser.getId());
        session.setAttribute("sessionUser", userPS);
        return "redirect:/login-form";
    }

    @GetMapping("/personal/user/update-form")
    public String personalUpdateForm() {
        return "personal/user/update-form";
    }

    @PostMapping("/personal/user/update")
    public String update(UserRequest.PersonalUpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");
        if (!reqDTO.getNewPassword().equals(reqDTO.getConfirmPassword())) throw new RuntimeException("입력한 비밀번호가 다릅니다.");
        if (!reqDTO.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$"))
            throw new RuntimeException("비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        if (reqDTO.getEmail() == null || !reqDTO.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new RuntimeException("올바른 이메일 형식이 아닙니다.");
        }
        if (reqDTO.getContactNumber() == null || !reqDTO.getContactNumber().matches("^010-\\d{4}-\\d{4}$")) {
            throw new RuntimeException("전화번호는 010-1234-5678 형식으로 입력해주세요.");
        }
        User userPS = userService.update(reqDTO, sessionUser.getId());
        session.setAttribute("sessionUser", userPS);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpServletResponse response, @RequestParam(required = false) String redirect) {

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
                return "redirect:/company/info/" + companyInfoId;
            } else {
                session.removeAttribute("companyInfoId");
                return "redirect:/company/main";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-form";
    }

    @GetMapping("/company/main")
    public String companyMain() {
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
    public String userJoin(UserRequest.PersonalJoinDTO reqDTO) {
        if (!reqDTO.getUsername().matches("^[a-zA-Z0-9*_]{4,20}$"))
            throw new RuntimeException("아이디는 4~20자, 영문/숫자/*/_만 가능합니다.");
        if (!reqDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$"))
            throw new RuntimeException("비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        if (reqDTO.getEmail() == null || !reqDTO.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new RuntimeException("올바른 이메일 형식이 아닙니다.");
        }
        if (reqDTO.getContactNumber() == null || !reqDTO.getContactNumber().matches("^010-\\d{4}-\\d{4}$")) {
            throw new RuntimeException("전화번호는 010-1234-5678 형식으로 입력해주세요.");
        }
        userService.join(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/company/user/join-form")
    public String companyJoinForm() {
        return "company/user/join-form";
    }

    @PostMapping("/company/user/join")
    public String companyJoin(UserRequest.CompanyJoinDTO reqDTO) {
        if (!reqDTO.getUsername().matches("^[a-zA-Z0-9*_]{4,20}$"))
            throw new RuntimeException("아이디는 4~20자, 영문/숫자/*/_만 가능합니다.");
        if (!reqDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$"))
            throw new RuntimeException("비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        if (reqDTO.getEmail() == null || !reqDTO.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new RuntimeException("올바른 이메일 형식이 아닙니다.");
        }
        if (reqDTO.getContactNumber() == null || !reqDTO.getContactNumber().matches("^010-\\d{4}-\\d{4}$")) {
            throw new RuntimeException("전화번호는 010-1234-5678 형식으로 입력해주세요.");
        }
        userService.join(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<JobPostingResponse.MainDTO.MainRecentJobPostingDTO> recent = jobPostingService.getRecentPostings();
        List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> popular = jobPostingService.getPopularJobPostings();

        JobPostingResponse.MainDTO mainDTO = new JobPostingResponse.MainDTO(recent, popular);

        request.setAttribute("model", mainDTO);
        return "personal/main/logout";
    }
}
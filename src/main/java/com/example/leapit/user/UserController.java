package com.example.leapit.user;

import com.example.leapit._core.util.Resp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;


    @GetMapping("/company/user/update-form")
    public String companyUpdateForm() {
        return "company/user/update-form";
    }

    @GetMapping("/personal/user/update-form")
    public String personalUpdateForm() {
        return "personal/user/update-form";
    }
    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpServletResponse response) {

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
        if (loginDTO.getRole().equals("personal")) {
            return "redirect:/";
        }else {
            return "redirect:/company/main";
        }
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
        if (!reqDTO.getUsername().matches("^[a-zA-Z0-9*_]{4,20}$")) throw new RuntimeException("아이디는 4~20자, 영문/숫자/*/_만 가능합니다.");
        if (!reqDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$"))throw new RuntimeException("비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        userService.join(reqDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/company/user/join-form")
    public String companyJoinForm() {
        return "company/user/join-form";
    }

    @PostMapping("/company/user/join")
    public String companyJoin(UserRequest.CompanyJoinDTO reqDTO) {
        if (!reqDTO.getUsername().matches("^[a-zA-Z0-9*_]{4,20}$")) throw new RuntimeException("아이디는 4~20자, 영문/숫자/*/_만 가능합니다.");
        if (!reqDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,16}$"))throw new RuntimeException("비밀번호는 8~16자, 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        userService.join(reqDTO);
        return "redirect:/login-form";
    }

}
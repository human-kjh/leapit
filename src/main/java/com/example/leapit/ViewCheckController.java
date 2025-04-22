package com.example.leapit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewCheckController {

    @GetMapping("/t1")
    public String t1() {
        return "login-form";
    }

    @GetMapping("/t2")
    public String t2() {
        return "company/applicant/detail";
    }

    @GetMapping("/t3")
    public String t3() {
        return "company/applicant/list";
    }

    @GetMapping("/t4")
    public String t4() {
        return "company/info/detail";
    }

    @GetMapping("/t5")
    public String t5() {
        return "company/info/save-form";
    }

    @GetMapping("/t6")
    public String t6() {
        return "company/info/update-form";
    }

    @GetMapping("/t7")
    public String t7() {
        return "company/jobposting/detail";
    }

    @GetMapping("/t8")
    public String t8() {
        return "company/jobposting/list";
    }

    @GetMapping("/t9")
    public String t9() {
        return "company/jobposting/save-form";
    }

    @GetMapping("/t10")
    public String t10() {
        return "company/jobposting/update-form";
    }

    @GetMapping("/t11")
    public String t11() {
        return "company/user/join-form";
    }

    @GetMapping("/t12")
    public String t12() {
        return "company/user/update-form";
    }

    @GetMapping("/t13")
    public String t13() {
        return "company/main";
    }

    @GetMapping("/t14")
    public String t14() {
        return "personal/board/detail";
    }

    @GetMapping("/t15")
    public String t15() {
        return "personal/board/list";
    }

    @GetMapping("/t16")
    public String t16() {
        return "personal/board/save-form";
    }

    @GetMapping("/t17")
    public String t17() {
        return "personal/board/update-form";
    }

    @GetMapping("/t18")
    public String t18() {
        return "personal/jobposting/detail";
    }

    @GetMapping("/t19")
    public String t19() {
        return "personal/jobposting/list";
    }

    @GetMapping("/t20")
    public String t20() {
        return "personal/main/logout";
    }

    @GetMapping("/t21")
    public String t21() {
        return "personal/main/login";
    }

    @GetMapping("/t22")
    public String t22() {
        return "personal/mypage/application";
    }

    @GetMapping("/t23")
    public String t23() {
        return "personal/mypage/jobposting";
    }

    @GetMapping("/t24")
    public String t24() {
        return "personal/resume/detail";
    }

    @GetMapping("/t25")
    public String t25() {
        return "personal/resume/list";
    }

    @GetMapping("/t26")
    public String t26() {
        return "personal/resume/save-form";
    }

    @GetMapping("/t27")
    public String t27() {
        return "personal/resume/update-form";
    }

    @GetMapping("/t28")
    public String t28() {
        return "personal/user/join-form";
    }

    @GetMapping("/t29")
    public String t29() {
        return "personal/user/update-form";
    }


}

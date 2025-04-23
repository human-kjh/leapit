package com.example.leapit.companyinfo;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class CompanyInfoController {
    private final CompanyInfoService companyInfoService;
    private final HttpSession session;

    @GetMapping("/company")
    public String index() {
        return "company/main";
    }


    @GetMapping("/company/info/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {


        CompanyInfoResponse.DetailDTO respDTO = companyInfoService.detail(id);
        request.setAttribute("model", respDTO);

        return "company/info/detail";
    }

    @GetMapping("/company/info/save-form")
    public String saveForm() {
        return "company/info/save-form";
    }

    @PostMapping("/company/info/save")
    public String save(CompanyInfoRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        companyInfoService.save(reqDTO, sessionUser);

        return "redirect:/company/info/";
    }


}

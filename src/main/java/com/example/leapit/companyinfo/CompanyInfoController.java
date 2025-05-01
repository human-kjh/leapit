package com.example.leapit.companyinfo;

import com.example.leapit._core.error.ex.Exception401;
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

    @GetMapping("/s/company/info/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        CompanyInfoResponse.DetailDTO respDTO = companyInfoService.detail(id, sessionUser.getId());
        request.setAttribute("model", respDTO);

        return "company/info/detail";
    }

    @GetMapping("/s/company/info/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        return "company/info/save-form";
    }


    @PostMapping("/s/company/info/save")
    public String save(CompanyInfoRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        CompanyInfo companyInfo = companyInfoService.save(reqDTO, sessionUser);

        session.setAttribute("companyInfoId", companyInfo.getId());

        return "redirect:/s/company/info/" + companyInfo.getId();
    }


    @GetMapping("/s/company/info/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        CompanyInfo companyInfo = companyInfoService.updateCheck(id,sessionUser.getId());
        request.setAttribute("model", companyInfo);

        return "company/info/update-form";
    }

    @PostMapping("/s/company/info/{id}/update")
    public String update(@PathVariable("id") Integer id, CompanyInfoRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        companyInfoService.update(id, sessionUser.getId(), reqDTO);

        return "redirect:/s/company/info/" + id;
    }

    // TODO : 기업정보만 삭제시 채용공고의 기업이름으로 인해 터지는 오류 발생 -> 기업 정보 삭제 기능 X 이후 회원 탈퇴로 처리

    // 구직자 - 기업상세
    @GetMapping("/personal/companyinfo/{id}")
    public String personalDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        CompanyInfo companyInfo = companyInfoService.findById(id);
        Integer companyUserId = companyInfo.getUser().getId();

        CompanyInfoResponse.DetailDTO respDTO = companyInfoService.detail(id, companyUserId);
        request.setAttribute("model", respDTO);

        return "personal/companyinfo/detail";
    }

}

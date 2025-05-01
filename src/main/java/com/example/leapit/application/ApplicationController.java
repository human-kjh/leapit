package com.example.leapit.application;

import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit._core.error.ex.ExceptionApi401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.resume.ResumeService;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;
    private final ResumeService resumeService;

    @GetMapping("/s/personal/mypage/bookmark")
    public String personalBookmark(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");
        ApplicationResponse.ApplicationBookmarkListDTO respDTO = applicationService.myBookmarkpage(sessionUser.getId());

        request.setAttribute("models", respDTO);
        return "personal/mypage/bookmark";
    }

    // 개인 지원 현황 관리
    @GetMapping("/s/personal/mypage/application")
    public String application(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        ApplicationResponse.ApplicationListViewDTO respDTO = applicationService.myApplicationPage(sessionUser.getId());
        request.setAttribute("models", respDTO);

        return "personal/mypage/application";
    }

    @GetMapping("/s/company/applicant/list")
    public String applicantList(ApplicationRequest.ApplicantListReqDTO reqDTO,
                                HttpServletRequest request,
                                @RequestParam(required = false, value = "passStatus", defaultValue = "전체") String passStatus,
                                @RequestParam(required = false, value = "isViewedStr") String isViewedStr,
                                @RequestParam(required = false, value = "isBookmark") String isBookmarkStr
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        Boolean isViewed = null;
        if ("열람".equals(isViewedStr)) {
            isViewed = true;
        } else if ("미열람".equals(isViewedStr)) {
            isViewed = false;
        }

        Boolean isBookmark = null;
        if ("true".equals(isBookmarkStr)) isBookmark = true;

        ApplicationResponse.ApplicantListPageDTO respDTO =
                applicationService.findApplicantPageWithFilters(sessionUser.getId(), reqDTO.getJobPostingId(), passStatus, isViewed, isBookmark);

        request.setAttribute("models", respDTO);

        return "company/applicant/list";
    }

    @GetMapping("/s/company/applicant/{id}")
    public String applicationDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        ApplicationResponse.DetailDTO detailDTO = applicationService.detail(id, sessionUser.getId());
        request.setAttribute("model", detailDTO);
        return "/company/applicant/detail";
    }

    @ResponseBody
    @PutMapping("/s/api/company/applicant/{id}/pass")
    public Resp<?> isPassedUpdate(@PathVariable("id") Integer id, @RequestBody ApplicationRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        applicationService.update(id, updateDTO, sessionUser.getId());
        return Resp.ok(null);
    }

    @GetMapping("/s/personal/jobposting/{id}/apply-form")
    public String ApplyForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new Exception401("로그인 후 이용하세요.");
        }

        // 지원서 작성에 필요한 데이터 조회
        ApplicationRequest.ApplyFormDTO applyFormDTO = applicationService.getApplyForm(id, sessionUser.getId());

        request.setAttribute("applyForm", applyFormDTO);

        // 지원 폼 페이지로 이동
        return "/personal/jobposting/apply";
    }

    // 채용공고에 이력서 지원하기
    @PostMapping("/personal/jobposting/{id}/apply")
    public String apply(@PathVariable("id") Integer jobPostingId, @Valid ApplicationRequest.ApplyReqDTO applyReqDTO, Errors errors) {

        // 세션에서 로그인한 사용자 정보 가져오기
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new Exception401("로그인 후 이용하세요.");
        }

        Integer userId = sessionUser.getId();

        applicationService.apply(applyReqDTO, userId);

        return "redirect:/s/personal/mypage/application";
    }
}

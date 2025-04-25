package com.example.leapit.application;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    // 개인 지원 현황 관리
    @GetMapping("/personal/mypage/application")
    public String application(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");


        ApplicationResponse.ApplicationListViewDTO respDTO = applicationService.findApplicationListByUserId(sessionUser.getId());
        request.setAttribute("models", respDTO);

        return "personal/mypage/application";
    }

    @GetMapping("/company/applicant/list")
    public String applicantList(ApplicationRequest.ApplicantListReqDTO reqDTO ,
                                HttpServletRequest request,
                                @RequestParam(required = false, value = "passStatus", defaultValue = "전체") String passStatus,
                                @RequestParam(required = false, value = "isViewedStr") String isViewedStr,
                                @RequestParam(required = false, value = "isBookmark") String isBookmarkStr
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        Boolean isViewed = null;
        if ("열람".equals(isViewedStr)) {
            isViewed = true;
        } else if ("미열람".equals(isViewedStr)) {
            isViewed = false;
        }

        Boolean isBookmark = null;
        if ("true".equals(isBookmarkStr)) isBookmark = true;

        ApplicationResponse.ApplicantListPageDTO respDTO =
                applicationService.findApplicantPageWithFilters(sessionUser.getId(), reqDTO.getJobPostingId(),passStatus,isViewed, isBookmark);

        request.setAttribute("models", respDTO);

        return "company/applicant/list";
    }
}

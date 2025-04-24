package com.example.leapit.application;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        // 지원 현황 통계
        ApplicationResponse.ApplicationStatusDto status = applicationService.statusByUserId(sessionUser.getId());

        // 지원 목록
        List<ApplicationResponse.ApplicationDto> applications = applicationService.getApplicationsByUserId(sessionUser.getId());

        request.setAttribute("model", status);
        request.setAttribute("models", applications);

        return "personal/mypage/application";
    }

    @GetMapping("/company/applicant/list")
    public String applicantList(ApplicationRequest.ApplicantListReqDTO reqDTO ,
                                HttpServletRequest request
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("로그인 후 이용");

        List<ApplicationResponse.CompanyeApplicantDto> applicants =
                applicationService.지원자목록조회(sessionUser.getId(), reqDTO.getJobPostingId());

        List<ApplicationResponse.IsClosedDTO> positions =
                applicationService.진행중과마감포지션조회(sessionUser.getId());

        request.setAttribute("models", applicants);
        request.setAttribute("positions", positions); // 뿌리는 List가 2개라 positions이라 별칭함
        request.setAttribute("req", reqDTO);

        return "company/applicant/list";
    }


}

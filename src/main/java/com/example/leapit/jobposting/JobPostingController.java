package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class JobPostingController {

    private final JobPostingService jobPostingService;

    // 채용 공고 등록 폼
    @GetMapping("/jobposting/save-form")
    public String showSaveForm() {
        return "company/jobposting/save-form";
    }

    // 채용 공고 등록 처리 메서드
    @PostMapping("/job-postings/save")
    public String createJobPosting(JobPostingRequest request,
                                   @SessionAttribute("sessionUser") User sessionUser,
                                   @RequestParam(value = "techStack", required = false) List<String> techStack) {
        request.setTechStack(techStack); // 폼에서 받은 기술 스택을 request 객체에 설정
        jobPostingService.createJobPosting(request, sessionUser);
        return "redirect:/company/jobposting/list";
    }

    // 채용 공고 삭제 처리 메서드
    @PostMapping("/job-postings/delete")
    public String deleteJobPosting(@RequestParam("id") Integer id) {
        jobPostingService.deleteJobPosting(id);
        return "redirect:/company/jobposting/list"; // 삭제 후 목록 페이지로 리다이렉트
    }

    // 구직자 - 채용공고 목록
    @GetMapping("/personal/jobposting/list")
    public String list(HttpServletRequest req) {
        List<JobPostingResponse.JobPostingDTO> jobpostingList = jobPostingService.getAllJobPostings();
        req.setAttribute("models", jobpostingList);
        return "personal/jobposting/list";
    }
}
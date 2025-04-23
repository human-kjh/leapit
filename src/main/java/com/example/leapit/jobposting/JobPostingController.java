package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // 채용 공고 수정 폼 보여주기 (Model import 없이 HttpServletRequest 사용)
    @GetMapping("/job-postings/edit-form/{id}")
    public String showEditForm(@PathVariable Integer id, HttpServletRequest request) {
        JobPosting jobPosting = jobPostingService.findById(id);
        request.setAttribute("jobPosting", jobPosting);
        return "company/jobposting/update-form"; // 수정 폼 템플릿 이름
    }

    // 채용 공고 수정 처리
    @PostMapping("/job-postings/edit/{id}")
    public String updateJobPosting(@PathVariable Integer id, JobPostingRequest request) {
        jobPostingService.updateJobPosting(id, request);
        return "redirect:/company/jobposting/list"; // 수정 후 목록 페이지로 리다이렉트
    }
}
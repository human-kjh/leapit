package com.example.leapit.jobposting;

import com.example.leapit.user.User;
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

}
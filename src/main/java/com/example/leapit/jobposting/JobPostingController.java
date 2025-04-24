package com.example.leapit.jobposting;

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
public class JobPostingController {

    private final JobPostingService jobPostingService;
    private final HttpSession session;

    // 채용 공고 목록 보기
    @GetMapping("/company/jobposting/list")
    public String list(HttpServletRequest request) {
        request.setAttribute("openJobPostings", jobPostingService.OpenJobPostings());
        request.setAttribute("closedJobPostings", jobPostingService.ClosedJobPostings());
        return "company/jobposting/list";
    }

    // 채용 공고 상세보기
    @GetMapping("/jobposting/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        JobPosting jobPosting = jobPostingService.findById(id);
        request.setAttribute("model", jobPosting);
        return "company/jobposting/detail";
    }

    // 채용 공고 등록 폼
    @GetMapping("/jobposting/save-form")
    public String saveForm() {
        return "company/jobposting/save-form";
    }

    // 채용 공고 등록
    @PostMapping("/jobposting/save")
    public String save(JobPostingRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        jobPostingService.save(saveDTO, sessionUser);
        return "redirect:/company/jobposting/list";
    }

    // 채용 공고 수정 폼
    @GetMapping("/jobposting/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        JobPosting updateDTO = jobPostingService.findById(id);

        request.setAttribute("model", updateDTO);

        return "company/jobposting/update-form";
    }

    // 채용 공고 수정
    @PostMapping("/jobposting/{id}/update")
    public String update(@PathVariable("id") Integer id, JobPostingRequest.UpdateDTO updateDTO) {
        jobPostingService.update(id, updateDTO);
        return "redirect:/jobposting/" + id;
    }

    // 채용 공고 삭제
    @PostMapping("/jobposting/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        jobPostingService.delete(id);
        return "redirect:/company/jobposting/list";
    }
}

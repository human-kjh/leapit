package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class JobPostingController {

    private final JobPostingService jobPostingService;

    // 채용 공고 등록 폼 보여주기
    @GetMapping("/jobposting/save-form")
    public String showSaveForm(Model model) {
        model.addAttribute("saveDTO", new JobPostingRequest.SaveDTO()); // 빈 SaveDTO 객체를 모델에 담아서 뷰로 전달
        return "company/jobposting/save-form";
    }

    // 채용 공고 등록 처리
    @PostMapping("/jobposting/save")
    public String createJobPosting(@ModelAttribute("saveDTO") JobPostingRequest.SaveDTO saveDTO, // 폼 데이터 바인딩을 위한 @ModelAttribute
                                   @SessionAttribute("sessionUser") User sessionUser, // 세션에서 로그인된 사용자 정보 가져오기
                                   @RequestParam(value = "techStack", required = false) List<String> techStack) { // 폼에서 전달된 기술 스택 파라미터 받기
        saveDTO.setTechStack(techStack); // 폼에서 받은 기술 스택을 SaveDTO 객체에 설정
        jobPostingService.createJobPosting(saveDTO, sessionUser); // 서비스에 SaveDTO와 사용자 정보 전달하여 채용 공고 생성
        return "redirect:/company/jobposting/list"; // 채용 공고 목록 페이지로 리다이렉트
    }

    // 채용 공고 삭제 처리
    @PostMapping("/jobposting/delete")
    public String deleteJobPosting(@RequestParam("id") Integer id) { // 폼에서 전달된 삭제할 채용 공고 ID 파라미터 받기
        jobPostingService.deleteJobPosting(id); // 서비스에 ID 전달하여 채용 공고 삭제
        return "redirect:/company/jobposting/list"; // 채용 공고 목록 페이지로 리다이렉트
    }

    // 채용 공고 수정 폼 보여주기
    @GetMapping("/jobposting/update-form/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) { // 경로 변수에서 수정할 채용 공고 ID 받기, Model 객체 주입
        JobPosting jobPosting = jobPostingService.findById(id); // ID로 채용 공고 정보 조회
        JobPostingRequest.UpdateDTO updateDTO = new JobPostingRequest.UpdateDTO(jobPosting); // 조회한 정보를 UpdateDTO로 변환
        model.addAttribute("updateDTO", updateDTO); // UpdateDTO 객체를 모델에 담아서 뷰로 전달
        model.addAttribute("jobPostingId", id); // 수정할 채용 공고 ID도 모델에 담아서 뷰에서 사용 가능하도록 함
        return "company/jobposting/update-form"; // "company/jobposting/update-form" 뷰 템플릿을 응답으로 반환
    }

    // 채용 공고 수정 처리
    @PostMapping("/jobposting/update/{id}")
    public String updateJobPosting(@PathVariable Integer id, @ModelAttribute("updateDTO") JobPostingRequest.UpdateDTO updateDTO, // 경로 변수에서 수정할 채용 공고 ID 받기, 폼 데이터 바인딩
                                   @RequestParam(value = "techStack", required = false) List<String> techStack) { // 폼에서 전달된 기술 스택 파라미터 받기
        updateDTO.setTechStack(techStack); // 폼에서 받은 기술 스택을 UpdateDTO 객체에 설정
        jobPostingService.updateJobPosting(id, updateDTO); // 서비스에 ID와 UpdateDTO 전달하여 채용 공고 수정
        return "redirect:/company/jobposting/list"; // 채용 공고 목록 페이지로 리다이렉트
    }

    // 채용 공고 상세 페이지 조회
    @GetMapping("/jobposting/{id}")
    public String showJobPostingDetail(@PathVariable Integer id, HttpServletRequest request) { // 경로 변수에서 조회할 채용 공고 ID 받기, HttpServletRequest 객체 주입
        JobPosting jobPosting = jobPostingService.findById(id); // ID로 채용 공고 정보 조회
        request.setAttribute("jobPosting", jobPosting); // 조회한 정보를 HttpServletRequest의 속성에 담아서 뷰로 전달
        return "company/jobposting/detail"; // "company/jobposting/detail" 뷰 템플릿을 응답으로 반환
    }

    // 채용 공고 목록 조회
    @GetMapping("/list")
    public String showJobList(Model model) { // Model 객체 주입
        List<JobPosting> ongoingJobPostings = jobPostingService.getOngoingJobPostings(); // 진행 중인 채용 공고 목록 조회
        List<JobPosting> closedJobPostings = jobPostingService.getClosedJobPostings();   // 마감된 채용 공고 목록 조회
        model.addAttribute("ongoingJobPostings", ongoingJobPostings); // 진행 중인 채용 공고 목록을 모델에 담기
        model.addAttribute("closedJobPostings", closedJobPostings);   // 마감된 채용 공고 목록을 모델에 담기
        return "company/jobposting/list"; // "company/jobposting/list" 뷰 템플릿을 응답으로 반환
    }
}
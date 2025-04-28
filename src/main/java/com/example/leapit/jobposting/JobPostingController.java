package com.example.leapit.jobposting;

import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.common.techstack.TechStackService;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
    import com.example.leapit.companyinfo.CompanyInfo;
    import com.example.leapit.companyinfo.CompanyInfoRepository;

    @RequiredArgsConstructor
    @Controller
    public class JobPostingController {

        private final JobPostingService jobPostingService;
        private final HttpSession session;
        private final TechStackService techStackService;
        private final JobPostingRepository jobPostingRepository;
        private final TechStackRepository techStackRepository;
        private final CompanyInfoRepository companyInfoRepository;

        // 채용 공고 목록 보기
        @GetMapping("/company/jobposting/list")
        public String companyList(HttpServletRequest request) {
            request.setAttribute("openJobPostings", jobPostingService.OpenJobPostings());
            request.setAttribute("closedJobPostings", jobPostingService.ClosedJobPostings());
            return "company/jobposting/list";
        }

        // 채용 공고 상세보기
        @GetMapping("/company/jobposting/{id}")
        public String companyDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
            JobPosting jobPosting = jobPostingService.findById(id);
            List<String> techStack = jobPostingService.getTechStacksByJobPostingId(id); // 기술 스택 목록 조회
            CompanyInfo companyInfo = companyInfoRepository.findByUserId(jobPosting.getUser().getId());  // 회사 정보 조회
            JobPostingResponse.AddressDTO addressDTO = jobPostingService.getJobPostingAddress(id);  // 주소 정보 조회

            request.setAttribute("model", jobPosting);
            request.setAttribute("techStack", techStack);
            request.setAttribute("company", companyInfo);  // 회사 정보
            request.setAttribute("address", addressDTO);  // 주소 정보

            return "company/jobposting/detail";
        }

        // 채용 공고 등록 폼
        @GetMapping("/jobposting/save-form")
        public String saveForm(HttpServletRequest request) {
            List<TechStack> techStacks = techStackService.getAllTechStacks();
            request.setAttribute("model", techStacks);
            return "company/jobposting/save-form";
        }

        // 채용 공고 등록
        @PostMapping("/jobposting/save")
        public String save(JobPostingRequest.SaveDTO saveDTO, String[] techStack) {
            User sessionUser = (User) session.getAttribute("sessionUser");

            jobPostingService.save(saveDTO, sessionUser, techStack);
            return "redirect:/company/jobposting/list";
        }

        // 채용 공고 수정 폼
        @GetMapping("/jobposting/{id}/update-form")
        public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
            JobPosting jobPosting = jobPostingRepository.findById(id);
            List<String> techStackList = jobPostingService.getTechStacksByJobPostingId(id);
            List<TechStack> allTechStacks = techStackRepository.findAll();

            // techStackList를 JSON 문자열로 직접 변환
            StringBuilder jsonTechStack = new StringBuilder("[");
            for (int i = 0; i < techStackList.size(); i++) {
                jsonTechStack.append("\"").append(techStackList.get(i)).append("\"");
                if (i < techStackList.size() - 1) {
                    jsonTechStack.append(",");
                }
            }
            jsonTechStack.append("]");

            request.setAttribute("model", jobPosting);
            request.setAttribute("techStackJson", jsonTechStack.toString()); // JSON 문자열을 모델에 담기
            request.setAttribute("allTechStacks", allTechStacks);

            return "company/jobposting/update-form";
        }

        // 채용 공고 수정
        @PostMapping("/jobposting/{id}/update")
        public String update(@PathVariable("id") Integer id, JobPostingRequest.UpdateDTO updateDTO,
                             @RequestParam(value = "techStacks", required = false) String[] techStacks) {
            jobPostingService.update(id, updateDTO, techStacks);
            return "redirect:/company/jobposting/" + id;
        }

        // 채용 공고 삭제
        @PostMapping("/jobposting/{id}/delete")
        public String delete(@PathVariable("id") Integer id) {
            jobPostingService.delete(id);
            return "redirect:/company/jobposting/list";
        }

        // 구직자 - 채용공고 목록
        @GetMapping("/personal/jobposting/list")
        public String personalList(HttpServletRequest req) {
            List<JobPostingResponse.JobPostingDTO> jobpostingList = jobPostingService.getAllJobPostings();
            req.setAttribute("models", jobpostingList);
            return "personal/jobposting/list";
        }

        // 구직자 - 채용공고 상세
        @GetMapping("/personal/jobposting/{id}")
        public String personalDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
            // 채용공고와 기술 스택, 회사 정보를 가져옴
            JobPosting jobPosting = jobPostingService.findById(id);  // 이미 정의된 jobPostingService.findById 메서드 사용
            List<String> techStack = jobPostingService.getTechStacksByJobPostingId(id); // 기술 스택 리스트
            CompanyInfo companyInfo = companyInfoRepository.findByUserId(jobPosting.getUser().getId());  // 회사 정보 조회

            // 주소 정보도 가져옴
            JobPostingResponse.AddressDTO addressDTO = jobPostingService.getJobPostingAddress(id);  // 주소 정보 조회

            // 모델에 필요한 정보들을 추가
            request.setAttribute("model", jobPosting);  // 채용공고 정보
            request.setAttribute("techStack", techStack);  // 기술 스택 정보
            request.setAttribute("company", companyInfo);  // 회사 정보
            request.setAttribute("address", addressDTO);  // 주소 정보

            // 상세 페이지를 반환 (Mustache 템플릿을 사용한다고 가정)
            return "personal/jobposting/detail";  // 해당 페이지로 이동
        }
    }
    // 구직자 - 채용공고 목록
    @GetMapping("/personal/jobposting/list")
    public String personalList(HttpServletRequest req, JobPostingRequest.JobPostingListRequestDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");


        if (Boolean.TRUE.equals(reqDTO.getIsPopular())) {
            reqDTO.setIsLatest(false);
        } else if (Boolean.TRUE.equals(reqDTO.getIsLatest())) {
            reqDTO.setIsPopular(false);
        } else {
            reqDTO.setIsPopular(false);
            reqDTO.setIsLatest(true);
        }

        // reqDTO에 담아놨습니다~
        Integer regionId = reqDTO.getRegionIdAsInteger();
        Integer subRegionId = reqDTO.getSubRegionIdAsInteger();
        Integer career = reqDTO.getCareerAsInteger();
        String techStackCode = reqDTO.getTechStackCodeOrNull();
        String selectedLabel = reqDTO.getSelectedLabelOrNull();
        boolean isPopular = reqDTO.isPopularTrue();
        boolean isLatest = reqDTO.isLatestTrue();

        Integer sessionUserId = (sessionUser != null) ? sessionUser.getId() : null;

        JobPostingResponse.JobPostingListFilterDTO respDTO =
                jobPostingService.공고목록페이지(
                        regionId,
                        subRegionId,
                        career,
                        techStackCode,
                        selectedLabel,
                        isPopular,
                        isLatest,
                        sessionUserId
                );

        req.setAttribute("models", respDTO);
        return "personal/jobposting/list";
    }
package com.example.leapit.jobposting;

import com.example.leapit.common.region.RegionRepository;
import com.example.leapit.common.region.RegionResponse;
import com.example.leapit.common.region.RegionService;
import com.example.leapit._core.error.ex.Exception400;
import com.example.leapit._core.error.ex.Exception401;
import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.common.techstack.TechStackService;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.companyinfo.CompanyInfoService;
import com.example.leapit.jobposting.bookmark.JobPostingBookmarkRepository;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class JobPostingController {

    private final JobPostingService jobPostingService;
    private final HttpSession session;
    private final TechStackService techStackService;
    private final JobPostingRepository jobPostingRepository;
    private final TechStackRepository techStackRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final RegionRepository regionRepository;
    private final RegionService regionService;
    private final JobPostingBookmarkRepository jobPostingBookmarkRepository;
    private final CompanyInfoService companyInfoService;


    // 채용 공고 목록 보기
    @GetMapping("/s/company/jobposting/list")
    public String companyList(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        request.setAttribute("openJobPostings", jobPostingService.OpenJobPostings(sessionUser.getId()));
        request.setAttribute("closedJobPostings", jobPostingService.ClosedJobPostings(sessionUser.getId()));
        return "company/jobposting/list";
    }

    // 채용 공고 상세보기
    @GetMapping("/s/company/jobposting/{id}")
    public String companyDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

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
    @GetMapping("/s/company/jobposting/save-form")
    public String saveForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        // 추가: 기업정보 조회
        Integer companyInfoId = companyInfoService.findCompanyInfoIdByUserId(sessionUser.getId());
        if (companyInfoId == null) {
            throw new Exception400("기업정보를 먼저 등록해야 채용공고를 작성할 수 있습니다.");
        }


        List<TechStack> techStacks = techStackService.getAllTechStacks();
        request.setAttribute("model", techStacks);
        return "company/jobposting/save-form";
    }

    // 채용 공고 등록
    @PostMapping("/s/company/jobposting/save")
    public String save(JobPostingRequest.SaveDTO saveDTO, String[] techStack) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        jobPostingService.save(saveDTO, sessionUser, techStack);
        return "redirect:/s/company/jobposting/list";
    }

    // 채용 공고 수정 폼
    @GetMapping("/s/company/jobposting/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");

        JobPosting jobPosting = jobPostingRepository.findById(id);
        List<String> techStackList = jobPostingService.getTechStacksByJobPostingId(id);
        List<TechStack> allTechStacks = techStackRepository.findAll();

        List<Map<String, Object>> stackModels = new ArrayList<>();
        for (TechStack stack : allTechStacks) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", stack.getCode());
            map.put("selected", techStackList.contains(stack.getCode()));
            stackModels.add(map);
        }

        // 시/도, 시/군/구 전체 리스트 + 선택 여부 포함해서 서비스에서 가져오기
        List<RegionResponse.SelectedRegionDTO> addressRegionList =
                regionService.getRegionsWithSelection(jobPosting.getAddressRegionId());

        List<RegionResponse.SelectedSubRegionDTO> addressSubRegionList =
                regionService.getSubRegionsWithSelection(jobPosting.getAddressRegionId(), jobPosting.getAddressSubRegionId());

        request.setAttribute("model", jobPosting);
        request.setAttribute("allTechStacks", stackModels);
        request.setAttribute("addressRegionList", addressRegionList);
        request.setAttribute("addressSubRegionList", addressSubRegionList);

        return "company/jobposting/update-form";
    }


    // 채용 공고 수정
    @PostMapping("/s/company/jobposting/{id}/update")
    public String update(@PathVariable("id") Integer id, JobPostingRequest.UpdateDTO updateDTO,
                         @RequestParam(value = "techStacks", required = false) String[] techStacks) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        jobPostingService.update(id, updateDTO, techStacks, sessionUser.getId());
        return "redirect:/s/company/jobposting/" + id;
    }

    // 채용 공고 삭제
    @PostMapping("/s/company/jobposting/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new Exception401("로그인 후 이용");


        jobPostingService.delete(id, sessionUser.getId());
        return "redirect:/s/company/jobposting/list";
    }


    // 구직자 - 채용공고 상세
    @GetMapping("/personal/jobposting/{id}")
    public String personalDetail(@PathVariable("id") Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 채용공고와 기술 스택, 회사 정보를 가져옴
        JobPosting jobPosting = jobPostingService.findById(id);  // 이미 정의된 jobPostingService.findById 메서드 사용
        List<String> techStack = jobPostingService.getTechStacksByJobPostingId(id); // 기술 스택 리스트
        CompanyInfo companyInfo = companyInfoRepository.findByUserId(jobPosting.getUser().getId());  // 회사 정보 조회

        // 주소 정보도 가져옴
        JobPostingResponse.AddressDTO addressDTO = jobPostingService.getJobPostingAddress(id);  // 주소 정보 조회

        // 북마크여부
        boolean isBookmarked = false;
        if (sessionUser != null) {
            isBookmarked = (jobPostingBookmarkRepository.findByUserIdAndJobPostingId(sessionUser.getId(), id) != null);
        }

        // 모델에 필요한 정보들을 추가
        request.setAttribute("model", jobPosting);  // 채용공고 정보
        request.setAttribute("techStack", techStack);  // 기술 스택 정보
        request.setAttribute("company", companyInfo);  // 회사 정보
        request.setAttribute("address", addressDTO);  // 주소 정보
        request.setAttribute("isLoggedIn", sessionUser != null);
        request.setAttribute("isBookmarked", isBookmarked);

        // 상세 페이지를 반환 (Mustache 템플릿을 사용한다고 가정)
        return "personal/jobposting/detail";  // 해당 페이지로 이동
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
        req.setAttribute("isLoggedIn", sessionUser != null);
        return "personal/jobposting/list";
    }
}
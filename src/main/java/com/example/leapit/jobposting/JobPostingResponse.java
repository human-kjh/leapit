package com.example.leapit.jobposting;

import com.example.leapit.companyinfo.CompanyInfoResponse;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JobPostingResponse {

    // 구직자 - 채용공고 목록
    @Data
    public static class JobPostingDTO {
        private Integer id;
        private String companyName;
        private String title;
        private LocalDate deadline;
        private int dDay;
        private String career;
        private String address; // ← 외부에서 주입
        private String image;
        private List<CompanyInfoResponse.DetailDTO.TechStackDTO> techStacks;

        // address는 외부에서 전달받음
        public JobPostingDTO(JobPosting jobPostings, List<JobPostingTechStack> techStacks, String address, String image, String companyName) {
            this.id = jobPostings.getId();
            this.title = jobPostings.getTitle();
            this.deadline = jobPostings.getDeadline();
            this.dDay = calculateDDay(deadline);
            this.career = formatCareer(jobPostings.getMinCareerLevel(), jobPostings.getMaxCareerLevel());
            this.address = address;
            this.image = image;
            this.companyName = companyName;
            this.techStacks = techStacks.stream()
                    .map(stack -> new CompanyInfoResponse.DetailDTO.TechStackDTO(stack.getTechStack().getCode()))
                    .collect(Collectors.toList());
        }

        private int calculateDDay(LocalDate deadline) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        }

        private String formatCareer(Integer min, Integer max) {
            if (min == null || max == null || (min == 0 && max == 0)) return "경력무관";
            if (min > max) return "경력 정보 오류";
            return min + "-" + max + "년";
        }
    }
}

package com.example.leapit.jobposting;

import com.example.leapit.companyinfo.CompanyInfo;
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

    // 구직자 - 메인페이지 채용공고 목록
    @Data
    public static class MainDTO {

        private List<MainRecentJobPostingDTO> recentJobPostings; // 최신공고 3개
        private List<MainPopularJobPostingDTO> popularJobPostings; // 인기공고 8개

        @Data
        public static class MainRecentJobPostingDTO {
            private Integer id;
            private String title;
            private String companyName;
            private String image;
            private boolean isActive;

            public MainRecentJobPostingDTO(JobPosting jp, CompanyInfo companyInfo, boolean isActive) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyInfo.getCompanyName();
                this.image = companyInfo.getImage();
                this.isActive = isActive;
            }
        }

        @Data
        public static class MainPopularJobPostingDTO {
            private Integer id;
            private String title;
            private String companyName;
            private String image;
            private String address;
            private String career;
            private int dDay;
            private int viewCount;
            private List<CompanyInfoResponse.DetailDTO.TechStackDTO> techStacks;

            public MainPopularJobPostingDTO(JobPosting jp,
                                            String companyName,
                                            String image,
                                            String address,
                                            List<JobPostingTechStack> techStacks) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyName;
                this.image = image;
                this.address = address;
                this.career = formatCareer(jp.getMinCareerLevel(), jp.getMaxCareerLevel());
                this.dDay = calculateDDay(jp.getDeadline());
                this.viewCount = jp.getViewCount();

                this.techStacks = techStacks.stream()
                        .map(stack -> new CompanyInfoResponse.DetailDTO.TechStackDTO(stack.getTechStack().getCode()))
                        .collect(Collectors.toList());
            }

            private String formatCareer(Integer min, Integer max) {
                if (min == null || max == null || (min == 0 && max == 0)) return "경력무관";
                if (min > max) return "경력 정보 오류";
                return min + "-" + max + "년";
            }

            private int calculateDDay(LocalDate deadline) {
                return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), deadline);
            }
        }


        public MainDTO(List<MainRecentJobPostingDTO> recent, List<MainPopularJobPostingDTO> popular) {
            this.recentJobPostings = recent;
            this.popularJobPostings = popular;
        }
    }

    @Data
    public static class AddressDTO {
        private String regionName;
        private String subRegionName;
        private String addressDetail;

        public AddressDTO(String regionName, String subRegionName, String addressDetail) {
            this.regionName = regionName;
            this.subRegionName = subRegionName;
            this.addressDetail = addressDetail;
        }
    }

}
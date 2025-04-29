package com.example.leapit.jobposting;

import com.example.leapit.common.positiontype.PositionTypeResponse;
import com.example.leapit.common.region.RegionResponse;
import com.example.leapit.common.techstack.TechStack;
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
        private boolean isBookmarked;
        private List<CompanyInfoResponse.DetailDTO.TechStackDTO> techStacks;

        // address는 외부에서 전달받음
        public JobPostingDTO(JobPosting jobPostings, List<JobPostingTechStack> techStacks, String address, String image, String companyName, boolean isBookmarked) {
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

            this.isBookmarked = isBookmarked;
        }

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
            private boolean isBookmarked;

            public MainRecentJobPostingDTO(JobPosting jp, CompanyInfo companyInfo, boolean isActive, boolean isBookmarked) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyInfo.getCompanyName();
                this.image = companyInfo.getImage();
                this.isActive = isActive;
                this.isBookmarked = isBookmarked;
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
            private boolean isBookmarked;
            private List<CompanyInfoResponse.DetailDTO.TechStackDTO> techStacks;

            public MainPopularJobPostingDTO(JobPosting jp,
                                            String companyName,
                                            String image,
                                            String address,
                                            List<JobPostingTechStack> techStacks, boolean isBookmarked) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyName;
                this.image = image;
                this.address = address;
                this.career = formatCareer(jp.getMinCareerLevel(), jp.getMaxCareerLevel());
                this.dDay = calculateDDay(jp.getDeadline());
                this.viewCount = jp.getViewCount();
                this.isBookmarked = isBookmarked;
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


    // 공고 목록 DTO (with 필터)
    // Name이 붙은 값들은 서버에 선택된 값 유지
    @Data
    public static class JobPostingListFilterDTO {
        private Integer selectedRegionId;
        private Integer selectedSubRegionId;
        private String selectedRegionName;
        private String selectedSubRegionName;
        private Integer selectedCareer;
        private String selectedCareerName;
        private String selectedTechStackCode;
        private String selectedTechStackName;
        private boolean hasAnyParam;
        private String selectedLabel;
        private boolean isPopular;
        private boolean isLatest;
        private List<PositionTypeResponse.PositionTypeDTO> positions;
        private List<TechStack> techStacks;
        private List<RegionResponse.RegionDTO> regions;
        private List<RegionResponse.SubRegionDTO> subRegions;
        private List<JobPostingResponse.JobPostingDTO> jobPostingList;

        public JobPostingListFilterDTO(
                List<PositionTypeResponse.PositionTypeDTO> positions,
                List<TechStack> techStacks, List<RegionResponse.RegionDTO> regions,
                List<RegionResponse.SubRegionDTO> subRegions,
                List<JobPostingDTO> jobPostingList,
                Integer selectedRegionId,
                Integer selectedSubRegionId,
                String selectedRegionName,
                String selectedSubRegionName,
                Integer selectedCareer,
                String selectedCareerName,
                String selectedTechStackCode,
                String selectedTechStackName,
                boolean hasAnyParam,
                String selectedLabel,
                boolean isPopular,
                boolean isLatest) {
            this.positions = positions;
            this.techStacks = techStacks;
            this.regions = regions;
            this.subRegions = subRegions;
            this.jobPostingList = jobPostingList;
            this.selectedRegionId = selectedRegionId;
            this.selectedSubRegionId = selectedSubRegionId;
            this.selectedRegionName = selectedRegionName;
            this.selectedSubRegionName = selectedSubRegionName;
            this.selectedCareer = selectedCareer;
            this.selectedCareerName = selectedCareerName;
            this.selectedTechStackCode = selectedTechStackCode;
            this.selectedTechStackName = selectedTechStackName;
            this.hasAnyParam = hasAnyParam;
            this.selectedLabel = selectedLabel;
            this.isPopular = isPopular;
            this.isLatest = isLatest;
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
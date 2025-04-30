package com.example.leapit.companyinfo;

import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyInfoResponse {

    @Data
    public static class DetailDTO {
        private Integer id;
        private String logoImage;
        private String companyName;
        private LocalDate establishmentDate; // 원본 날짜
        private String formattedEstablishmentInfo; // 포맷된 문자열
        private String address;
        private String mainService;
        private String introduction;
        private String image;
        private String benefit;
        private Integer jobPostingCount; // 공고 수만 남김

        private List<JobPostingDTO> jobPostings; // 공고들

        @Data
        public static class JobPostingDTO {
            private Integer id;
            private String title;
            private LocalDate deadline;
            private String dDayLabel;
            private List<TechStackDTO> techStacks;

            public JobPostingDTO(JobPosting jobPostings, List<JobPostingTechStack> techStacks) {
                this.id = jobPostings.getId();
                this.title = jobPostings.getTitle();
                this.deadline = jobPostings.getDeadline();
                this.dDayLabel = calculateDdayLabel(deadline);
                this.techStacks = techStacks.stream()
                        .map(stack -> new TechStackDTO(stack.getTechStack().getCode()))
                        .collect(Collectors.toList());
            }

            private String calculateDdayLabel(LocalDate deadline) {
                if (deadline == null) return "마감일 없음";
                int days = (int) ChronoUnit.DAYS.between(LocalDate.now(), deadline);
                return days < 0 ? "마감" : "D-" + days;
            }
        }

        @Data
        public static class TechStackDTO {
            private String name;

            public TechStackDTO(String name) {
                this.name = name;
            }
        }

        public DetailDTO(CompanyInfo companyInfo, Integer userId, Integer jobPostingCount, List<JobPosting> jobPostings, List<JobPostingTechStack> techStacks) {
            this.id = companyInfo.getId();
            this.logoImage = companyInfo.getLogoImage();
            this.companyName = companyInfo.getCompanyName();
            this.establishmentDate = companyInfo.getEstablishmentDate();
            this.formattedEstablishmentInfo = formatEstablishmentInfo(companyInfo.getEstablishmentDate());
            this.address = companyInfo.getAddress();
            this.mainService = companyInfo.getMainService();
            this.introduction = companyInfo.getIntroduction();
            this.image = companyInfo.getImage();
            this.benefit = companyInfo.getBenefit();
            this.jobPostingCount = jobPostingCount;

            List<JobPostingDTO> jobPostingsDTO = new ArrayList<>();


            for (JobPosting jobPosting : jobPostings) {
                // 마감일이 지난 공고는 건너뛰기
//                if (jobPosting.getDeadline().isBefore(LocalDate.now())) {
//                    continue;
//                }

                List<JobPostingTechStack> matchedStacks = techStacks.stream()
                        .filter(stack -> stack.getJobPosting() != null && stack.getJobPosting().getId().equals(jobPosting.getId()))
                        .collect(Collectors.toList());

                JobPostingDTO jobPostingDTO = new JobPostingDTO(jobPosting, matchedStacks);
                jobPostingsDTO.add(jobPostingDTO);
            }
            this.jobPostings = jobPostingsDTO;
        }

        private String formatEstablishmentInfo(LocalDate date) {
            if (date == null) return "정보 없음";

            LocalDate now = LocalDate.now();
            Period period = Period.between(date, now);
            int years = period.getYears();

            String year = String.valueOf(date.getYear());
            String month = String.valueOf(date.getMonthValue());

            return years + "년차 (" + year + "년 " + month + "월 설립)";
        }
    }
}
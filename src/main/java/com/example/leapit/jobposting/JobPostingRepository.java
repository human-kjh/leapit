package com.example.leapit.jobposting;

import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoResponse;
import com.example.leapit.jobposting.bookmark.JobPostingBookmark;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    // 등록
    public void save(JobPosting jobPosting) {
        em.persist(jobPosting);
    }

    public void saveJobPostingTechStack(JobPostingTechStack jobPostingTechStack) {
        em.persist(jobPostingTechStack);
    }

    // 삭제
    public void deleteById(Integer id) {
        JobPosting jobPosting = em.find(JobPosting.class, id);
        em.remove(jobPosting);
    }

    // 단건 조회
    public JobPosting findById(Integer id) {
        return em.find(JobPosting.class, id);
    }

    // 진행 중인 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineOpen(LocalDate deadline, Integer userId) {
        return em.createQuery(
                        "select jp from JobPosting jp where jp.deadline >= :deadline and jp.user.id = :userId",
                        JobPosting.class)
                .setParameter("deadline", deadline)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 마감된 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineClosed(LocalDate deadline, Integer userId) {
        return em.createQuery(
                        "select jp from JobPosting jp where jp.deadline < :deadline and jp.user.id = :userId",
                        JobPosting.class)
                .setParameter("deadline", deadline)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 특정 채용 공고에 등록된 기술 스택 코드 목록 조회 (상세 페이지, 수정 페이지에서 필요)
    public List<String> findTechStacksByJobPostingId(Integer jobPostingId) {
        return em.createQuery("SELECT jpts.techStack.code FROM JobPostingTechStack jpts WHERE jpts.jobPosting.id = :jobPostingId", String.class)
                .setParameter("jobPostingId", jobPostingId)
                .getResultList();

    }

    // 채용공고 & 해당 채용공고의 기술스택 조회
    public List<Object[]> findJobPostingsWithTechStacksByUserId(Integer userId) {
        Query query = em.createQuery(
                "SELECT j, t FROM JobPosting j " +
                        "LEFT JOIN JobPostingTechStack t ON t.jobPosting.id = j.id " +
                        "WHERE j.user.id = :userId"
        );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // 채용중인 포지션 카운트 조회 (마감일이 오늘 이후인)
    public Long countByUserIdAndDeadlineAfter(Integer userId) {
        Query query = em.createQuery("SELECT COUNT(j) FROM JobPosting j WHERE j.user.id = :userId AND j.deadline >= CURRENT_DATE");
        query.setParameter("userId", userId);
        return (Long) query.getSingleResult();
    }

    public JobPosting findByJobPostingId(Integer jobPostingId) {
        return em.find(JobPosting.class, jobPostingId);
    }

    // 주소 - 시 조회
    public String findByRegion(Integer id) {
        Query query = em.createNativeQuery("select R.name from job_posting_tb J inner join  region_tb R  on R.id = J.address_region_id where J.id = ?");
        query.setParameter(1, id);

        return (String) query.getSingleResult();
    }

    // 주소 - 구 조회
    public String findBySubRegion(Integer id) {
        Query query = em.createNativeQuery("select S.name from job_posting_tb J inner join  sub_region_tb S  on S.id = J.address_sub_region_id where J.id = ?");
        query.setParameter(1, id);
        return (String) query.getSingleResult();

    }

    // 채용공고 & 해당 채용공고의 기술스택 전체조회
    public List<Object[]> findAllJobPostingsWithTechStacks() {
        LocalDate today = LocalDate.now();

        Query query = em.createQuery(
                "SELECT jp, jpts FROM JobPosting jp " +
                        "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id where jp.deadline >= :today"
        );
        query.setParameter("today", today);
        return query.getResultList();
    }


    public List<JobPostingResponse.JobPostingDTO> findAllJobPostingsWithTechStacksByFilter(
            Integer regionId, Integer subRegionId, Integer career, String techStackCode, String selectedLabel,
            boolean isPopular, boolean isLatest, Integer sessionUserId) {

        LocalDate today = LocalDate.now();

        StringBuilder jpql = new StringBuilder(
                "SELECT jp, jpts, ci " +
                        "FROM JobPosting jp " +
                        "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id " +
                        "LEFT JOIN CompanyInfo ci ON jp.user.id = ci.user.id " +
                        "WHERE jp.deadline >= :today"
        );

        if (regionId != null) {
            jpql.append(" AND jp.addressRegionId = :regionId");
        }
        if (subRegionId != null) {
            jpql.append(" AND jp.addressSubRegionId = :subRegionId");
        }

        // 3년차
//        if (career != null) {
//            jpql.append(" AND jp.minCareerLevel <= :career");
//        }
        // 3년차 이하인 거 다 띄움
        if (career != null) {
            jpql.append(" AND :career >= jp.minCareerLevel");
        }
        if (selectedLabel != null) {
            jpql.append(" AND jp.positionType = :selectedLabel");
        }

        if (techStackCode != null) {
            jpql.append(" AND EXISTS (" +
                    "SELECT 1 FROM JobPostingTechStack jpts2 " +
                    "WHERE jpts2.jobPosting.id = jp.id " +
                    "AND jpts2.techStack.code = :techStackCode" +
                    ")");
        }

        // 정렬
        if (isPopular && isLatest) {
            jpql.append(" ORDER BY jp.viewCount DESC, jp.createdAt DESC");
        } else if (isPopular) {
            jpql.append(" ORDER BY jp.viewCount DESC");
        } else if (isLatest) {
            jpql.append(" ORDER BY jp.createdAt DESC");
        } else {
            jpql.append(" ORDER BY jp.id DESC");
        }

        Query query = em.createQuery(jpql.toString(), Object[].class);
        query.setParameter("today", today);

        if (regionId != null) query.setParameter("regionId", regionId);
        if (subRegionId != null) query.setParameter("subRegionId", subRegionId);
        if (career != null) query.setParameter("career", career);
        if (techStackCode != null) query.setParameter("techStackCode", techStackCode);
        if (selectedLabel != null) query.setParameter("selectedLabel", selectedLabel);

        List<Object[]> results = query.getResultList();
        List<JobPostingResponse.JobPostingDTO> dtos = new ArrayList<>();

        Integer lastJobPostingId = null;
        JobPostingResponse.JobPostingDTO currentDTO = null;

        for (Object[] result : results) {
            JobPosting jobPosting = (JobPosting) result[0];
            JobPostingTechStack techStack = (JobPostingTechStack) result[1];
            CompanyInfo companyInfo = (CompanyInfo) result[2];

            boolean isBookmarked = isBookmarked(jobPosting.getId(), sessionUserId);

            if (!jobPosting.getId().equals(lastJobPostingId)) {
                String address = companyInfo != null ? companyInfo.getAddress() : "주소 없음";
                String image = companyInfo != null ? companyInfo.getImage() : "이미지 없음";
                String companyName = companyInfo != null ? companyInfo.getCompanyName() : "회사명 없음";

                List<JobPostingTechStack> techStacks = new ArrayList<>();
                if (techStack != null) {
                    techStacks.add(techStack);
                }

                // JobPostingDTO 생성 시, isBookmarked 값 전달
                currentDTO = new JobPostingResponse.JobPostingDTO(
                        jobPosting, techStacks, address, image, companyName, isBookmarked
                );
                dtos.add(currentDTO);

                lastJobPostingId = jobPosting.getId();
            } else {
                if (techStack != null && currentDTO != null) {
                    currentDTO.getTechStacks().add(new CompanyInfoResponse.DetailDTO.TechStackDTO(
                            techStack.getTechStack().getCode()
                    ));
                }
            }
        }

        return dtos;
    }

    // 북마크 확인 메서드
    public boolean isBookmarked(Integer jobPostingId, Integer sessionUserId) {
        JobPostingBookmark bookmark = em.createQuery(
                        "SELECT b FROM JobPostingBookmark b WHERE b.jobPosting.id = :jobPostingId AND b.user.id = :sessionUserId",
                        JobPostingBookmark.class)
                .setParameter("jobPostingId", jobPostingId)
                .setParameter("sessionUserId", sessionUserId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return bookmark != null;
    }


    // 구직자 메인페이지 - 인기공고 8개
    public List<Object[]> findTop8PopularJobPostingsWithTechStacks() {
        LocalDate today = LocalDate.now();

        // 1. 마감일 이후 + viewCount 기준 상위 8개 채용공고 ID 조회
        List<Integer> top8Ids = em.createQuery(
                        "SELECT jp.id FROM JobPosting jp " +
                                "WHERE jp.deadline >= :today " +
                                "ORDER BY jp.viewCount DESC", Integer.class)
                .setParameter("today", today)
                .setMaxResults(8)
                .getResultList();

        if (top8Ids.isEmpty()) return new ArrayList<>();

        // 2. 해당 ID들 기준으로 기술스택 조인 포함 재조회
        return em.createQuery(
                        "SELECT jp, jpts FROM JobPosting jp " +
                                "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id " +
                                "WHERE jp.id IN :ids", Object[].class)
                .setParameter("ids", top8Ids)
                .getResultList();
    }

    // 구직자 메인페이지 - 최신공고 3개
    public List<JobPosting> findTop3RecentJobPostings() {
        LocalDate today = LocalDate.now();

        Query query = em.createQuery(
                "SELECT jp FROM JobPosting jp " +
                        "WHERE jp.deadline >= :today " +
                        "ORDER BY jp.createdAt DESC"
        );
        query.setParameter("today", today);
        query.setMaxResults(3);

        return query.getResultList();
    }


    // Native Query로 주소 정보를 조회하여 AddressDTO로 반환
    public JobPostingResponse.AddressDTO findJobPostingAddressById(Integer id) {
        String sql = "SELECT r.name AS region_name, sr.name AS sub_region_name, jp.address_detail "
                + "FROM job_posting_tb jp "
                + "JOIN region_tb r ON jp.address_region_id = r.id "
                + "JOIN sub_region_tb sr ON jp.address_sub_region_id = sr.id "
                + "WHERE jp.id = ?";

        Query query = em.createNativeQuery(sql);
        query.setParameter(1, id);

        try {
            Object[] result = (Object[]) query.getSingleResult();
            return new JobPostingResponse.AddressDTO((String) result[0], (String) result[1], (String) result[2]);
        } catch (Exception e) {
            return null;
        }
    }
}


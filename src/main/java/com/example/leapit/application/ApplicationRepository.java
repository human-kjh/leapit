package com.example.leapit.application;

import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.jobposting.JobPosting;
import com.example.leapit.resume.Resume;
import com.example.leapit.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApplicationRepository {

    private final EntityManager em;

    public List<ApplicationResponse.CompanyeApplicantDto> findAllApplicantsByFilter(
            Integer companyUserId, Integer jobPostingId, String passStatus, Boolean isViewed, Boolean isBookmark) {

        String sql = """
                    SELECT
                        att.id AS applicationId,
                        rt.id AS resumeId,
                        ut.name AS applicantName,
                        jpt.title AS jobTitle,
                        att.applied_date AS appliedDate,
                        CASE WHEN abt.id IS NOT NULL THEN TRUE ELSE FALSE END AS isBookmarked,
                        CASE
                            WHEN att.is_viewed = false THEN '미열람'
                            WHEN att.is_viewed = true AND att.is_passed IS NULL THEN '열람'
                            WHEN att.is_passed = true THEN '합격'
                            WHEN att.is_passed = false THEN '불합격'
                        END AS evaluationStatus,
                        att.is_viewed AS isViewed
                    FROM APPLICATION_TB att
                    JOIN RESUME_TB rt ON att.resume_id = rt.id
                    JOIN USER_TB ut ON rt.user_id = ut.id
                    JOIN JOB_POSTING_TB jpt ON att.job_posting_id = jpt.id
                    JOIN USER_TB comut ON jpt.user_id = comut.id
                    LEFT JOIN APPLICATION_BOOKMARK_TB abt 
                        ON abt.application_id = att.id AND abt.user_id = :companyUserId
                    WHERE comut.id = :companyUserId
                """;

        if (jobPostingId != null) {
            sql += " AND jpt.id = :jobPostingId";
        }

        if ("합격".equals(passStatus)) {
            sql += " AND att.is_passed = true";
        } else if ("불합격".equals(passStatus)) {
            sql += " AND att.is_passed = false";
        }

        if (isViewed != null) {
            sql += " AND att.is_viewed = " + (isViewed ? "true" : "false");
        }

        if (Boolean.TRUE.equals(isBookmark)) {
            sql += " AND abt.id IS NOT NULL";
        }

        sql += " ORDER BY att.applied_date DESC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("companyUserId", companyUserId);
        if (jobPostingId != null) query.setParameter("jobPostingId", jobPostingId);

        List<Object[]> objects = query.getResultList();
        List<ApplicationResponse.CompanyeApplicantDto> object = new ArrayList<>();

        for (Object[] obs : objects) {
            Integer applicationId = ((int) obs[0]);
            Integer resumeId = ((int) obs[1]);
            String applicantName = (String) obs[2];
            String jobTitle = (String) obs[3];
            LocalDate appliedDate = ((java.sql.Date) obs[4]).toLocalDate();
            Boolean isBookmarked = (Boolean) obs[5];
            String evaluationStatus = (String) obs[6];
            Boolean isViewedResult = (Boolean) obs[7];

            ApplicationResponse.CompanyeApplicantDto dto = new ApplicationResponse.CompanyeApplicantDto(
                    applicationId, resumeId, applicantName, jobTitle, appliedDate,
                    isBookmarked, evaluationStatus, isViewedResult
            );
            object.add(dto);
        }
        return object;
    }


    public List<ApplicationResponse.IsClosedDTO> positionAndIsClosedDtoBycompanyUserIds(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.application.ApplicationResponse$IsClosedDTO(
                        jpt.id,
                        jpt.title,
                        CASE WHEN jpt.deadline < CURRENT_DATE THEN true ELSE false END
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, ApplicationResponse.IsClosedDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }


    // 지원 현황 통계
    public ApplicationResponse.ApplicationStatusDto findSummaryByUserId(Integer userId) {
        String jpql = """
                    SELECT COUNT(a), 
                           COUNT(CASE WHEN a.isPassed = true THEN 1 ELSE 0 END), 
                           COUNT(CASE WHEN a.isPassed = false THEN 1 ELSE 0 END)
                    FROM Application a
                    JOIN a.resume r
                    JOIN r.user u
                    WHERE u.id = :userId
                """;

        Object[] result = (Object[]) em.createQuery(jpql)
                .setParameter("userId", userId)
                .getSingleResult();

        return new ApplicationResponse.ApplicationStatusDto(
                (Long) result[0],
                (Long) result[1],
                (Long) result[2]
        );
    }

    // 지원 현황 목록
    public List<ApplicationResponse.ApplicationDto> findApplicationsByUserId(Integer userId) {
        String jpql = """
                    SELECT\s
                        ci.companyName,\s
                        jp.title,\s
                        a.appliedDate,\s
                        r.id AS resumeId,\s
                        jp.id AS jobPostingId,
                    CASE WHEN a.isPassed = true\s
                    THEN '합격'
                    WHEN a.isPassed = false THEN '불합격'
                    ELSE '미정'
                    END AS result,
                    CASE WHEN a.isPassed = true
                    THEN 'bg-primary'
                    WHEN a.isPassed = false
                    THEN 'bg-danger'
                    ELSE 'bg-secondary'
                    END AS badgeClass
                    FROM Application a
                    JOIN a.resume r
                    JOIN r.user u
                    JOIN a.jobPosting jp
                    JOIN jp.user company
                    JOIN CompanyInfo ci ON ci.user.id = company.id
                    WHERE u.id = :userId
                    ORDER BY a.appliedDate DESC
                    
                """;

        List<Object[]> resultList = em.createQuery(jpql, Object[].class)
                .setParameter("userId", userId)
                .getResultList();

        return resultList.stream()
                .map(row -> new ApplicationResponse.ApplicationDto(
                        (String) row[0],
                        (String) row[1],
                        (LocalDate) row[2],
                        (Integer) row[3],
                        (Integer) row[4],
                        (String) row[5],
                        (String) row[6]
                ))
                .toList();
    }

    public Application findByApplicationId(Integer applicationId) {
        return em.find(Application.class, applicationId);
    }

    public List<Application> findAllByResumeId(int resumeId) {
        Query query = em.createQuery("Select a from Application a where a.resume.id = :resumeId");
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }

    public ApplicationRequest.ApplyFormDTO findApplyFormInfo(Integer jobPostingId, Integer userId) {
        // 채용공고 조회
        JobPosting jobPosting = em.find(JobPosting.class, jobPostingId);

        // 지원자 조회
        User applicantUser = em.find(User.class, userId);

        // 이력서 조회
        List<Resume> resumes = em.createQuery(
                        "SELECT r FROM Resume r WHERE r.user.id = :userId", Resume.class)
                .setParameter("userId", userId)
                .getResultList();

        // 회사 정보 조회 (채용공고 작성자 기준)
        Integer companyUserId = jobPosting.getUser().getId();
        List<CompanyInfo> companyInfos = em.createQuery(
                        "SELECT c FROM CompanyInfo c JOIN c.user u WHERE u.id = :companyUserId", CompanyInfo.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();

        CompanyInfo companyInfo = companyInfos.isEmpty() ? null : companyInfos.get(0);

        return new ApplicationRequest.ApplyFormDTO(jobPosting, companyInfo, applicantUser, resumes);
    }


    public List<Resume> findAllResumesByUserId(Integer userId) {
        Query query = em.createQuery("SELECT r FROM Resume r WHERE r.user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public Resume findResumeById(Integer resumeId) {
        return em.find(Resume.class, resumeId);
    }

    public ApplicationRequest.JobPostingInfoDto findJobPostingInfoDto(Integer jobPostingId) {
        JobPosting jobPosting = em.find(JobPosting.class, jobPostingId);
        if (jobPosting == null) {
            return null; // 또는 예외 처리
        }
        return new ApplicationRequest.JobPostingInfoDto(jobPosting);
    }

    // 채용공고에 이력서 지원하기
    public void save(Application application) {
        em.persist(application);
    }

    public boolean checkIfAlreadyApplied(Integer userId, Integer jobPostingId) { // userId, jobPostingId 받도록 수정
        String jpql = """
                SELECT COUNT(a) > 0
                FROM Application a
                WHERE a.resume.user.id = :userId
                AND a.jobPosting.id = :jobPostingId
                """;

        // 쿼리에 넘겨주는 파라미터 이름도 userId로 맞춰줌
        return em.createQuery(jpql, Boolean.class)
                .setParameter("userId", userId)
                .setParameter("jobPostingId", jobPostingId)
                .getSingleResult();
    }

    public Application findByResumeIdAndJobPostingId(Integer applicationId) {
        String jpql = "SELECT a FROM Application a WHERE a.id = :applicationId";
        return em.createQuery(jpql, Application.class)
                .setParameter("applicationId", applicationId)
                .getSingleResult();
    }



}

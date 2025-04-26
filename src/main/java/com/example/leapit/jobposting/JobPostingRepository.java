package com.example.leapit.jobposting;

import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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


    // 진행 중인 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineOpen(LocalDate deadline) {
        return em.createQuery("select jp from JobPosting jp where jp.deadline >= :deadline", JobPosting.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }

    // 마감된 채용 공고 목록 조회
    public List<JobPosting> findByDeadlineClosed(LocalDate deadline) {
        return em.createQuery("select jp from JobPosting jp where jp.deadline < :deadline", JobPosting.class)
                .setParameter("deadline", deadline)
                .getResultList();
    }

    // 특정 채용 공고에 등록된 기술 스택 코드 목록 조회 (상세 페이지, 수정 페이지에서 필요)
    public List<String> findTechStacksByJobPostingId(Integer jobPostingId) {
        return em.createQuery("SELECT jpts.techStack.code FROM JobPostingTechStack jpts WHERE jpts.jobPosting.id = :jobPostingId", String.class)
                .setParameter("jobPostingId", jobPostingId)
                .getResultList();
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
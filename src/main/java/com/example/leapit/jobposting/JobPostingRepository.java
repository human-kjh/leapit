package com.example.leapit.jobposting;

import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void deleteJobPostingTechStacksByJobPostingId(Integer jobPostingId) {
        em.createQuery("DELETE FROM JobPostingTechStack jpts WHERE jpts.jobPosting.id = :jobPostingId")
                .setParameter("jobPostingId", jobPostingId)
                .executeUpdate();
    }
}
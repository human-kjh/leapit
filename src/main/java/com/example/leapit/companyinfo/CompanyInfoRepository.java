package com.example.leapit.companyinfo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CompanyInfoRepository {
    private final EntityManager em;

    public void save(CompanyInfo companyInfo) {
        em.persist(companyInfo);
    }

    public CompanyInfo findById(Integer id) {
        return em.find(CompanyInfo.class, id);
    }

    public CompanyInfo findByUserId(Integer userId) {
        Query query = em.createQuery("select c from CompanyInfo c where c.user.id = :userId", CompanyInfo.class);
        query.setParameter("userId", userId);
        try {
            return (CompanyInfo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}

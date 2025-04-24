package com.example.leapit.companyinfo;

import jakarta.persistence.EntityManager;
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

    public void deleteById(Integer id) {
        CompanyInfo companyInfo = em.find(CompanyInfo.class, id);
        em.remove(companyInfo);
    }
}

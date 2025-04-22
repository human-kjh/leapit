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
}

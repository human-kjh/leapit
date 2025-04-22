package com.example.leapit.companyinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyInfoService {
    private final CompanyInfoRepository companyInfoRepository;
}

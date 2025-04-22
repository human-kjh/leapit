package com.example.leapit.companyinfo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CompanyInfoController {
    private final CompanyInfoService companyInfoService;
}

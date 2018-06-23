package com.upp.reverseauction.service;

import com.upp.reverseauction.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(final CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void save() {

    }
}

package com.upp.reverseauction.service;

import com.upp.reverseauction.model.BusinessCategory;
import com.upp.reverseauction.repository.BusinessCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessCategoryService {

    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    public BusinessCategoryService(final BusinessCategoryRepository businessCategoryRepository) {
        this.businessCategoryRepository = businessCategoryRepository;
    }

    public void save() {

    }

    public List<BusinessCategory> findAll() {
        return businessCategoryRepository.findAll();
    }
}

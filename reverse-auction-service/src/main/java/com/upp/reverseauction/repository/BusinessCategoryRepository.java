package com.upp.reverseauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upp.reverseauction.model.BusinessCategory;

import java.util.List;

public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory, Long> {
	
	BusinessCategory findById(long id);

	List<BusinessCategory> findByIdIsIn(List<Long> ids);

	BusinessCategory findByName(String name);
}

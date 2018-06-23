package com.upp.reverseauction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upp.reverseauction.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	Company findById(long id);

	@Query("Select c from Company c join c.businessCategory bc where bc.id = :businessCategoryId")
	List<Company> findAllByCategory(@Param("businessCategoryId") long businessCategoryId);

	@Query("Select c from Company c join c.agent agent where agent.id = :agentId")
	Company findCompanyByAgent(@Param("agentId") long agentId);
}

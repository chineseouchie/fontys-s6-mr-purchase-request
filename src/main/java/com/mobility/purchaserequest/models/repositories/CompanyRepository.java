package com.mobility.purchaserequest.models.repositories;

import com.mobility.purchaserequest.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findByUuid(String uuid);
}

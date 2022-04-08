package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyUuid(String companyUuid);
    Company findByCompanyName(String companyName);
}

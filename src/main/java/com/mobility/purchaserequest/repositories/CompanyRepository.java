package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.models.Offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByUuid(String uuid);
}

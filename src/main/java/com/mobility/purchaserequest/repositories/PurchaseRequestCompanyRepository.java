package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestCompanyRepository extends JpaRepository<PurchaseRequestCompany, Long> {
}

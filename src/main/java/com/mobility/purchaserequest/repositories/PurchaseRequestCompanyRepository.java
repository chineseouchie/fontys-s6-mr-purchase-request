package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRequestCompanyRepository extends JpaRepository<PurchaseRequestCompany, Long> {
	List<PurchaseRequestCompany> getAllByCompanyId(Long id);

	List<PurchaseRequestCompany> getAllByCompanyIdAndAcceptedIsNull(Long id);

	PurchaseRequestCompany getByUuid(String uuid);

	PurchaseRequestCompany getByUuidAndCompanyUuid(String uuid, String companyUuid);

	PurchaseRequestCompany getByUuidAndCompanyUuidAndAcceptedIsNull(String uuid, String companyUuid);

	List<PurchaseRequestCompany> findAllByCompanyUuid(String companyUuid);

	List<PurchaseRequestCompany> findAllByPurchaseRequestUuidAndAcceptedIsTrue(String purchaseRequestUuid);
}

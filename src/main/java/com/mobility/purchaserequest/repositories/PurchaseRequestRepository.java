package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.PurchaseRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
    PurchaseRequest findByUuid(String purchase_request_uuid);
}
package com.mobility.offer.repositories;

import com.mobility.offer.models.PurchaseRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, Long> {
    PurchaseRequest findByUuid(String uuid);
}
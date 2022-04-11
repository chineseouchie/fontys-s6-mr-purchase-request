package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.PerformedPurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformedPurchaseRequestsRepository extends JpaRepository<PerformedPurchaseRequest, Long> {
}


package com.mobility.purchaserequest.models.repositories;

import com.mobility.purchaserequest.models.Offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findByUuid(String offerUuid);
}

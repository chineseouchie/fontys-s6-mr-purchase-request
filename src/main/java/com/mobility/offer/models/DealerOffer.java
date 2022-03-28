package com.mobility.offer.models;

import javax.persistence.*;

import lombok.*;

@Table(name="dealer_offer")
public class DealerOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
    @Column(name="dealer_offer_id")
    private Long id;
    @Column(name="dealer_uuid")
    @Setter(AccessLevel.PROTECTED)
	private String dealerUuid;
}

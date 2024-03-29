package com.mobility.purchaserequest.models;

import java.math.BigDecimal;
import java.util.*;
import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "purchase_request")
public class PurchaseRequest implements Serializable {
	// Purchase request Id
	@Id
	@Setter(AccessLevel.PROTECTED)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_request_id")
	private Long id;

	// Purchase request uuid (Microservice foreign key)
	// @Setter(AccessLevel.PROTECTED)
	@Column(name = "purchase_request_uuid")
	private String uuid;

	@OneToOne()
	@JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
	private Offer offer;

	// Delivery date (Unix timestamp)
	@Column(name = "delivery_date")
	private Integer deliveryDate;

	// Delivery price
	@Column(name = "delivery_price")
	private BigDecimal deliveryPrice;

	public PurchaseRequest(Offer offer, Integer deliveryDate, BigDecimal deliveryPrice) {
		this.uuid = UUID.randomUUID().toString();
		this.offer = offer;
		this.deliveryDate = deliveryDate;
		this.deliveryPrice = deliveryPrice;
	}

	public PurchaseRequest(String uuid, Offer offer, Integer deliveryDate, BigDecimal deliveryPrice) {
		this.uuid = uuid;
		this.offer = offer;
		this.deliveryDate = deliveryDate;
		this.deliveryPrice = deliveryPrice;
	}
}

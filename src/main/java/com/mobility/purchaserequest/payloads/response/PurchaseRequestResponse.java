package com.mobility.purchaserequest.payloads.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.models.Vehicle;

@NoArgsConstructor
@Getter
@Setter
public class PurchaseRequestResponse {
	private String uuid;
	private Vehicle vehicle;
	private int delivery_date;
	private BigInteger delivery_price;

	public PurchaseRequestResponse(PurchaseRequest pr) {
		this.uuid = pr.getUuid();
		this.vehicle = pr.getOffer().getVehicle();
		this.delivery_date = pr.getDeliveryDate();
		this.delivery_price = pr.getDeliveryPrice();
	}
}

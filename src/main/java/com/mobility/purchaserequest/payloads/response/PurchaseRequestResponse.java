package com.mobility.purchaserequest.payloads.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.models.Vehicle;

@NoArgsConstructor
@Getter
@Setter
public class PurchaseRequestResponse {
	private String uuid;
	private int delivery_date;
	private BigDecimal delivery_price;
	private String brand;
	private String model;
	private String image_url;
	private String car_uuid;
	private String color;

	public PurchaseRequestResponse(PurchaseRequest pr) {
		Vehicle vehicle = pr.getOffer().getVehicle();
		this.uuid = pr.getUuid();
		this.delivery_date = pr.getDeliveryDate();
		this.delivery_price = pr.getDeliveryPrice();
		this.color = pr.getOffer().getColor();
		this.brand = vehicle.getBrandName();
		this.model = vehicle.getModelName();
		this.image_url = vehicle.getImageUrl();
		this.car_uuid = vehicle.getUuid();
	}

	public static List<PurchaseRequestResponse> convertList (List<PurchaseRequest> list) {
		List<PurchaseRequestResponse> result = new ArrayList<>();
		list.forEach((request) -> {
			result.add(new PurchaseRequestResponse(request));
		});
		return result;
	}
}

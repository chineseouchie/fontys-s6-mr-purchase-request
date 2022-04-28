package com.mobility.purchaserequest.models;

import javax.persistence.*;

import org.json.JSONObject;
import org.springframework.lang.Nullable;

import lombok.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "offer")
public class Offer implements Serializable {
	// Id (primary key)
	@Id
	@Setter(AccessLevel.PROTECTED)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offer_id")
	private Long id;

	// Uuid (Microservice foreign key)
	@Setter(AccessLevel.PROTECTED)
	@Column(name = "offer_uuid")
	private String uuid;

	// User uuid (Microservice foreign key)
	@Setter(AccessLevel.PROTECTED)
	@Column(name = "user_uuid")
	private String userUuid;

	@OneToOne()
	@JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
	private Vehicle vehicle;

	// Date (unix timestamp)
	@Column(name = "date")
	@Nullable
	private int date;

	// Color of vehicle in offer
	@Column(name = "vehicle_color")
	private String color;

	public Offer(Long id, String uuid, String userUuid, Vehicle vehicle, int date, String color) {
		this.id = id;
		this.uuid = uuid;
		this.userUuid = userUuid;
		this.vehicle = vehicle;
		this.date = date;
		this.color = color;
	}

	public Offer(String uuid, String userUuid, Vehicle vehicle, int date, String color) {
		this.uuid = uuid;
		this.userUuid = userUuid;
		this.vehicle = vehicle;
		this.date = date;
		this.color = color;
	}

	public Offer(JSONObject jsonObject, Vehicle vehicle) {
		this.uuid = jsonObject.getString("offerUuid");
		this.userUuid = jsonObject.getJSONObject("customer").getString("uuid");
		this.vehicle = vehicle;
		this.date = jsonObject.getInt("creation_date");
		this.color = jsonObject.getString("color");
	}
}

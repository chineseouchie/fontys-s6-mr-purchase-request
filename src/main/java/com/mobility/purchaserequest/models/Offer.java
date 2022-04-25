package com.mobility.purchaserequest.models;

import javax.persistence.*;

import org.json.JSONObject;
import org.springframework.lang.Nullable;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "offer")
public class Offer {
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

	public Offer(Long id, String uuid, String userUuid, Vehicle vehicle, int date) {
		this.id = id;
		this.uuid = uuid;
		this.userUuid = userUuid;
		this.vehicle = vehicle;
		this.date = date;
	}

	public Offer(String uuid, String userUuid, Vehicle vehicle, int date) {
		this.uuid = uuid;
		this.userUuid = userUuid;
		this.vehicle = vehicle;
		this.date = date;
	}

	public Offer(JSONObject jsonObject, Vehicle vehicle) {
		this.uuid = jsonObject.getString("offerUuid");
		this.userUuid = jsonObject.getJSONObject("customer").getString("uuid");
		this.vehicle = vehicle;
		this.date = jsonObject.getInt("creation_date");
	}
}

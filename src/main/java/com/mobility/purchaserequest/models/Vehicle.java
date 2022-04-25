package com.mobility.purchaserequest.models;

import javax.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.json.JSONObject;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vehicle")
public class Vehicle implements Serializable {
	// Vehicle id
	@Id
	@Setter(AccessLevel.PROTECTED)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Long id;

	// Vehicle uuid (microservice foreign key)
	@Setter(AccessLevel.PROTECTED)
	@Column(name = "vehicle_uuid")
	private String uuid;

	// Model name
	@Column(name = "model_name")
	private String modelName;

	// Brand name
	@Column(name = "brand_name")
	private String brandName;

	// Color
	@Column(name = "color")
	private String color;

	// Image url
	@Column(name = "image_url")
	private String imageUrl;

	public Vehicle(long id, String uuid, String modelName, String brandName, String color, String imageUrl) {
		this.id = id;
		this.uuid = uuid;
		this.modelName = modelName;
		this.brandName = brandName;
		this.color = color;
		this.imageUrl = imageUrl;
	}

	public Vehicle(JSONObject jsonObject) {
		this.id = jsonObject.getJSONObject("vehicle").getLong("vehicle_id");
		this.uuid = jsonObject.getJSONObject("vehicle").getString("uuid");
		this.modelName = jsonObject.getJSONObject("vehicle").getString("model");
		this.brandName = "Fix Brandname Plox";
		this.color = jsonObject.getString("color");
	}
}

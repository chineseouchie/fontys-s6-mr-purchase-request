package com.mobility.offer.models;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
    @Column(name="vehicle_id")
    private Long id;
    @Column(name="vehicle_uuid")
    @Setter(AccessLevel.PROTECTED)
	private String uuid;
    @Column(name="model_name")
    private String modelName;
    @Column(name="brand_name")
    private String brandName;
    @Column(name="color")
    private String color;
    @Column(name="image_url")
    private String imageUrl;
    @Column(name="numberplate")
    private String numberplate;
}

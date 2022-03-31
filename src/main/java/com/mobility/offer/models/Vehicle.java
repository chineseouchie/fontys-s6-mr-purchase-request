package com.mobility.offer.models;

import java.math.BigInteger;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="vehicle")
public class Vehicle {
    //Vehicle id
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vehicle_id")
    private Integer id;
    
    //Vehicle uuid (microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="vehicle_uuid")
    private String vehicleUuid;

    //Model name
    @Column(name="model_name")
    private String modelName;

    //Brand name
    @Column(name="brand_name")
    private String brandName;

    //Color
    @Column(name="color")
    private String color;

    //Image url
    @Column(name="image_url")
    private String imageUrl;

    public Vehicle(Integer id, String vehicleUuid, String modelName, String brandName, String color, String imageUrl) {
        this.id = id;
        this.vehicleUuid = vehicleUuid;
        this.modelName = modelName;
        this.brandName = brandName;
        this.color = color;
        this.imageUrl = imageUrl;
    }
}

package com.mobility.offer.models;

import java.math.BigInteger;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="purchase_request")
public class PurchaseRequest {
    //Purchase request Id
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchase_request_id")
    private Long id;

    //Purchase request uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="purchase_request_uuid")
    private String uuid;

    //Offer uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="offer_uuid")
    private String offerUuid;

    //Vehicle id (Foreign key) 
    @Setter(AccessLevel.PROTECTED)
    @Column(name="vehicle_id")
    private Long vehicleId;
    
    //Delivery date (Unix timestamp)
    @Column(name="delivery_date")
    private Integer deliveryDate;

    //Delivery price
    @Column(name="delivery_price")
    private BigInteger deliveryPrice;

    //Declined
    @Column(name="declined")
    private Boolean declined;
}

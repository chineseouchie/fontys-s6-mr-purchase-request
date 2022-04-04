package com.mobility.purchaserequest.models;

import java.math.BigInteger;

import javax.persistence.*;

import org.springframework.lang.Nullable;

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

    //The vehicle to be purchased by the company
    @Setter(AccessLevel.PROTECTED)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
    //Delivery date (Unix timestamp)
    @Column(name="delivery_date")
    private Integer deliveryDate;

    //Delivery price
    @Column(name="delivery_price")
    private BigInteger deliveryPrice;

    //Declined
    @Column(name="declined")
    @Nullable
    private Boolean declined;

    public PurchaseRequest(String purchase_request_uuid, String offer_uuid, Vehicle vehicle, Integer deliveryDate, BigInteger deliveryPrice) {
        this.uuid = purchase_request_uuid;
        this.offerUuid = offer_uuid;
        this.vehicle = vehicle;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
    }
}
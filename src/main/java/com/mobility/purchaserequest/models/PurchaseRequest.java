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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
    private Offer offer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dealer_id", referencedColumnName = "dealer_id")
    private Dealer dealer;
    
    //Delivery date (Unix timestamp)
    @Column(name="delivery_date")
    private Integer deliveryDate;

    //Delivery price
    @Column(name="delivery_price")
    private BigInteger deliveryPrice;

    //Accepted
    @Column(name="accepted")
    @Nullable
    private Boolean accepted;
}
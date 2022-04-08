package com.mobility.purchaserequest.models;

import java.math.BigInteger;
import java.util.UUID;

import javax.persistence.*;

import org.springframework.lang.Nullable;

import lombok.*;

@Entity
@NoArgsConstructor
@Getter @Setter
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
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;
    
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

    public PurchaseRequest(Offer offer, Company company, Integer deliveryDate, BigInteger deliveryPrice) {
        this.uuid = UUID.randomUUID().toString();
        this.offer = offer;
        this.company = company;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
    }

    public PurchaseRequest(Long id, String uuid, Offer offer, Company company, Integer deliveryDate, BigInteger deliveryPrice){
        this.id = id;
        this.uuid = uuid;
        this.offer = offer;
        this.company = company;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
    }
}
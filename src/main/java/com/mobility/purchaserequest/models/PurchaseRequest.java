package com.mobility.purchaserequest.models;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "purchaseRequestsCompanys",
            joinColumns = {@JoinColumn(name = "purchase_request_id")},
            inverseJoinColumns = {@JoinColumn(name = "company_Id")})
    private Set<Company> companies = new HashSet<>();

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

    public PurchaseRequest(Offer offer, Integer deliveryDate, BigInteger deliveryPrice) {
        this.uuid = UUID.randomUUID().toString();
        this.offer = offer;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
    }

    public PurchaseRequest(Long id, String uuid, Offer offer, Integer deliveryDate, BigInteger deliveryPrice){
        this.id = id;
        this.uuid = uuid;
        this.offer = offer;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
    }
}
package com.mobility.purchaserequest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="performed_purchase_requests")
public class PerformedPurchaseRequest {

    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="performed_purchase_request_id")
    private Long id;

    //Purchase request uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="performed_purchase_request_uuid")
    private String uuid;

    @Column(name="company_id")
    private Long companyId;

    //Accepted
    @Column(name="accepted")
    @Nullable
    private Boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JsonIgnore
    @JoinColumn(name = "purchase_request_id")
    private PurchaseRequest purchaseRequest;


    public PerformedPurchaseRequest(Long companyId, Boolean accepted, PurchaseRequest purchaseRequest) {
        this.uuid = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.accepted = accepted;
        this.purchaseRequest = purchaseRequest;
    }

    public PerformedPurchaseRequest( String uuid, Long companyId, PurchaseRequest purchaseRequest){
        this.uuid = uuid;
        this.companyId = companyId;
        this.purchaseRequest = purchaseRequest;
    }
}

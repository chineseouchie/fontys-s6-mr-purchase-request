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
    private String companyId;

    //Accepted
    @Column(name="accepted")
    @Nullable
    private Boolean accepted;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "performedPurchaseRequests")
    @JsonIgnore
    private List<PurchaseRequest> purchaseRequestSet = new ArrayList<>();


    public PerformedPurchaseRequest(String companyId, Boolean accepted) {
        this.uuid = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.accepted = accepted;
    }

    public PerformedPurchaseRequest(Long id, String uuid, String companyId, Boolean accepted){
        this.id = id;
        this.uuid = uuid;
        this.companyId = companyId;
        this.accepted = accepted;
    }
}

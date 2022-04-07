package com.mobility.purchaserequest.models;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="dealer")
public class Dealer {
    //Id (primary key)
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dealer_id")
    private Long id;

    //Company uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="company_uuid")
    private String companyUuid;

    @Column(name="company_name")
    private String companyName;
}

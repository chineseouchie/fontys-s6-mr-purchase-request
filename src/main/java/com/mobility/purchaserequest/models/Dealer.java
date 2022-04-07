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

    //User uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="user_uuid")
    private String userUuid;

    //Company uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="company_uuid")
    private String companyUuid;

    @Column(name="company_name")
    private String companyName;

    @Column(name="user_first_name")
    private String userFirstName;

    @Column(name="user_last_name")
    private String userLastName;
}

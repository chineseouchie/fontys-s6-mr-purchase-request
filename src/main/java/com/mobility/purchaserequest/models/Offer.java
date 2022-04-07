package com.mobility.purchaserequest.models;

import javax.persistence.*;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;

import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="offer")
public class Offer {
    //Id (primary key)
    @Id
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="offer_id")
    private Long id;

    //Uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="offer_uuid")
    private String Uuid;

    //User uuid (Microservice foreign key)
    @Setter(AccessLevel.PROTECTED)
    @Column(name="user_uuid")
    private String userUuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;

    //Date (unix timestamp)
    @Column(name="date")
    @Nullable
    private Integer date;
}
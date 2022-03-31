package com.mobility.offer.payloads;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatePurchaseRequestRequest {
    //Vehicle uuid
    @NotBlank
    private String vehicleUuid;
    //The offer's uuid
    @NotBlank
    private String offerUuid;
    //Delivery date (unix timestamp)
    @NotBlank
    private Integer deliveryDate;
    //Delivery price
    @NotBlank
    private BigInteger deliveryPrice;
}
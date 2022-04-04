package com.mobility.purchaserequest.payloads;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatePurchaseRequestRequest {
    //Vehicle information
    //Vehicle uuid
    @NotBlank
    private String vehicleUuid;
    //Vehicle model
    @NotBlank
    private String modelName;
    //Vehicle brand name
    @NotBlank
    private String brandName;
    //Vehicle color
    @NotBlank
    private String color;
    //Vehicle imageUrl
    @NotBlank
    private String imageUrl;

    //Purchase request information
    //Delivery date (unix timestamp)
    @NotBlank
    private Integer deliveryDate;
    //Delivery price
    @NotBlank
    private BigInteger deliveryPrice;
    //Offer Uuid (to be included in the purchase request)
    @NotBlank 
    private String offerUuid;
}
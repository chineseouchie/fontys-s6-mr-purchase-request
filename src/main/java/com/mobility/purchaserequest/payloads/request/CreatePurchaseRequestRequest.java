package com.mobility.purchaserequest.payloads.request;

import java.math.BigInteger;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CreatePurchaseRequestRequest {
    @NotBlank
    private String offerUuid;
    @NotBlank
    private String companyUuid;
    @NotBlank
    private BigInteger deliveryPrice;
    //Unix timestamp
    @NotBlank
    private Integer deliveryDate;
}
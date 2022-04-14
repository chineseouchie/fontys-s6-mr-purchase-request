package com.mobility.purchaserequest.payloads.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@NoArgsConstructor
@Getter
@Setter
public class GetPurchaseRequestByDealerResponse {

    @NotBlank
    private String uuid;

    @NotBlank
 	private Integer deliveryDate;

    @NotBlank
    private BigInteger deliveryPrice;

    @NotBlank
    private String purchaseUuid;

}

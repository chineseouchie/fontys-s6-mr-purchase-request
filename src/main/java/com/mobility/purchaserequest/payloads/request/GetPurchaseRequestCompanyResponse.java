package com.mobility.purchaserequest.payloads.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@NoArgsConstructor
@Getter
@Setter
public class GetPurchaseRequestCompanyResponse {

    @NotBlank
    private String uuid;

    @NotBlank
 	private Integer delivery_date;

    @NotBlank
    private BigInteger delivery_price;

    @NotBlank
    private String purchase_request_uuid;

    @NotBlank
    private String brand_name;

    @NotBlank
    private String model_name;

}

package com.mobility.purchaserequest.payloads.request;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreatePurchaseRequestRequest {
    @NotBlank
    private String offerUuid;
    @NotEmpty
    private List<String> companyUuids;
    @NotNull
    private BigDecimal deliveryPrice;
    @NotNull
    private Integer deliveryDate;
}
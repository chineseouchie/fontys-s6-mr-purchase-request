package com.mobility.purchaserequest.payloads.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AcceptPurchaseRequestRequest {
    private String purchaseRequestUuid;
    private String companyUuid;
}

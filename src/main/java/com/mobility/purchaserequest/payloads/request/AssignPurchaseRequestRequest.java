package com.mobility.purchaserequest.payloads.request;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AssignPurchaseRequestRequest {
    private String purchase_request_uuid;
    private String purchase_request_company_uuid;
}

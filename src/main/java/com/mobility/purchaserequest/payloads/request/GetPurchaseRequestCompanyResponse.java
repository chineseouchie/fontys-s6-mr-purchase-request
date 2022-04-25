package com.mobility.purchaserequest.payloads.request;

import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class GetPurchaseRequestCompanyResponse {

    @NotBlank
    private String uuid;

    @NotBlank
    private Integer delivery_date;

    @NotBlank
    private BigDecimal delivery_price;

    @NotBlank
    private String purchase_request_uuid;

    @NotBlank
    private String brand_name;

    @NotBlank
    private String model_name;

    public GetPurchaseRequestCompanyResponse(PurchaseRequestCompany purchaseRequestCompany) {
        if (purchaseRequestCompany.getPurchaseRequest() != null) {
            this.uuid = purchaseRequestCompany.getUuid();
            this.delivery_date = purchaseRequestCompany.getPurchaseRequest().getDeliveryDate();
            this.delivery_price = purchaseRequestCompany.getPurchaseRequest().getDeliveryPrice();
            this.purchase_request_uuid = purchaseRequestCompany.getPurchaseRequest().getUuid();
            this.brand_name = purchaseRequestCompany.getPurchaseRequest().getOffer().getVehicle().getBrandName();
            this.model_name = purchaseRequestCompany.getPurchaseRequest().getOffer().getVehicle().getModelName();
        }
    }

    public static final List<GetPurchaseRequestCompanyResponse> convertPurchaseRequestCompanyList(List<PurchaseRequestCompany> purchaseRequestCompanies) {
        List<GetPurchaseRequestCompanyResponse> reponses = new ArrayList<>();
        purchaseRequestCompanies.forEach((purchaseRequestCompany -> {
            reponses.add(new GetPurchaseRequestCompanyResponse(purchaseRequestCompany));
        }));
        return reponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPurchaseRequestCompanyResponse that = (GetPurchaseRequestCompanyResponse) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(delivery_date, that.delivery_date) && Objects.equals(delivery_price, that.delivery_price) && Objects.equals(purchase_request_uuid, that.purchase_request_uuid) && Objects.equals(brand_name, that.brand_name) && Objects.equals(model_name, that.model_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, delivery_date, delivery_price, purchase_request_uuid, brand_name, model_name);
    }
}

package com.mobility.purchaserequest.payloads.response;

import com.mobility.purchaserequest.models.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DealerResponse {
    private String uuid;
    private String name;

    public DealerResponse(Company company) {
        this.uuid = company.getUuid();
        this.name = company.getCompanyName();
    }

    public static final List<DealerResponse> convertCompanyToResponse(List<Company> dealers) {
        List<DealerResponse> responses = new ArrayList<>();
        dealers.forEach(dealer -> {
            responses.add(new DealerResponse(dealer));
        });
        return responses;
    }
}

package com.mobility.purchaserequest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "purchase_request_company")
public class PurchaseRequestCompany {
	@Id
	@Setter(AccessLevel.PROTECTED)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_request_company_id")
	private Long id;

	// Purchase request uuid (Microservice foreign key)
	@Setter(AccessLevel.PROTECTED)
	@Column(name = "purchase_request_company_uuid")
	private String uuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", referencedColumnName = "company_id")
	private Company company;

	// Accepted
	@Column(name = "accepted")
	@Nullable
	private Boolean accepted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "purchase_request_id")
	private PurchaseRequest purchaseRequest;

	public PurchaseRequestCompany(Company company, Boolean accepted, PurchaseRequest purchaseRequest) {
		this.uuid = UUID.randomUUID().toString();
		this.company = company;
		this.accepted = accepted;
		this.purchaseRequest = purchaseRequest;
	}

	public PurchaseRequestCompany(long id, String uuid, Company company, PurchaseRequest purchaseRequest) {
		this.id = id;
		this.uuid = uuid;
		this.company = company;
		this.purchaseRequest = purchaseRequest;
	}

	public PurchaseRequestCompany(String uuid, Company company, PurchaseRequest purchaseRequest) {
		this.uuid = uuid;
		this.company = company;
		this.purchaseRequest = purchaseRequest;
	}
}

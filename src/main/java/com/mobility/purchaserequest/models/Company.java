package com.mobility.purchaserequest.models;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "company")
public class Company {
	// Id (primary key)
	@Id
	@Setter(AccessLevel.PROTECTED)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;

	// Company uuid (Microservice foreign key)
	@Setter(AccessLevel.PROTECTED)
	@Column(name = "company_uuid")
	private String uuid;

	@Column(name = "company_name")
	private String companyName;

	public Company(Long id, String uuid, String companyName) {
		this.id = Long.valueOf(id);
		this.uuid = uuid;
		this.companyName = companyName;
	}

	public Company(String uuid, String companyName) {
		this.uuid = uuid;
		this.companyName = companyName;
	}
}

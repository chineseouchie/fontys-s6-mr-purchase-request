package com.mobility.purchaserequest.controllers;

import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import com.mobility.purchaserequest.payloads.request.GetPurchaseRequestCompanyResponse;
import com.mobility.purchaserequest.repositories.PurchaseRequestCompanyRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.payloads.request.AcceptPurchaseRequestRequest;
import com.mobility.purchaserequest.payloads.request.CreatePurchaseRequestRequest;
import com.mobility.purchaserequest.repositories.CompanyRepository;
import com.mobility.purchaserequest.repositories.OfferRepository;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/v1/purchase-request")
public class PurchaseRequestController {
	private PurchaseRequestCompanyRepository purchaseRequestCompanyRepository;
	private PurchaseRequestRepository purchaseRequestRepository;
	private OfferRepository offerRepository;
	private CompanyRepository companyRepository;

	public PurchaseRequestController(PurchaseRequestRepository purchaseRequestRepository,
			OfferRepository offerRepository, CompanyRepository companyRepository,
			PurchaseRequestCompanyRepository purchaseRequestCompanyRepository) {

		this.purchaseRequestRepository = purchaseRequestRepository;
		this.offerRepository = offerRepository;
		this.companyRepository = companyRepository;
		this.purchaseRequestCompanyRepository = purchaseRequestCompanyRepository;
	}

	// Create a new purchase request
	@PostMapping(path = "/create")
	public ResponseEntity<Map<String, String>> create(@Valid @RequestBody CreatePurchaseRequestRequest request) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("message", "Internal server error.");

		try {
			// Fetch the offer. And check if it's valid.
			Offer offer = this.offerRepository.findByUuid(request.getOfferUuid());
			if (offer != null && offer.getUuid().equals(request.getOfferUuid())) {

				// Fetch all the companies the request will be created for.
				List<Company> companies = new ArrayList<Company>();
				for (String companyUuid : request.getCompanyUuids()) {
					Company company = this.companyRepository.findByUuid(companyUuid);
					if (company != null) {
						companies.add(company);
					}
				}

				if (companies != null && companies.size() != 0) {
					// Create the purchase request
					PurchaseRequest purchaseRequest = new PurchaseRequest(
							offer,
							request.getDeliveryDate(),
							request.getDeliveryPrice());

					// Create a new entity for each company that receives the purchase request.
					List<PurchaseRequestCompany> purchaseRequestsToCompanies = new ArrayList<PurchaseRequestCompany>();
					for (Company company : companies) {
						PurchaseRequestCompany purchaseRequestCompany = new PurchaseRequestCompany(
								company,
								null,
								purchaseRequest);
						purchaseRequestsToCompanies.add(purchaseRequestCompany);
					}

					// Save the purchase request
					this.purchaseRequestRepository.save(purchaseRequest);
					// Save the purchaseRequestCompanies
					this.purchaseRequestCompanyRepository.saveAll(purchaseRequestsToCompanies);
					httpStatus = HttpStatus.CREATED;
					responseBody.put("message", "Succesfully created the purchase requests.");
				} else {
					httpStatus = HttpStatus.NOT_FOUND;
					responseBody.put("message", "Invalid company uuid\'s.");
				}
			} else {
				responseBody.put("message", "Invalid offer uuid.");
				httpStatus = HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
	}

	@PostMapping(path = "/accept")
	public ResponseEntity<Map<String, String>> acceptPurchaseRequest(
			@RequestBody AcceptPurchaseRequestRequest request) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();

		try {
			PurchaseRequest purchaseRequestToAccept = this.purchaseRequestRepository
					.findByUuid(request.getPurchaseRequestUuid());
			List<PurchaseRequestCompany> allPurchaseRequestsSentToCompanies = this.purchaseRequestCompanyRepository
					.findAllByPurchaseRequestUuid(purchaseRequestToAccept.getUuid());

			if (purchaseRequestToAccept != null && purchaseRequestToAccept.getUuid() != null) {
				if (allPurchaseRequestsSentToCompanies.size() > 0) {
					for (PurchaseRequestCompany purchaseRequestCompany : allPurchaseRequestsSentToCompanies) {
						purchaseRequestCompany.setAccepted(false);
						if (purchaseRequestCompany.getCompany().getUuid().equals(request.getCompanyUuid())) {
							purchaseRequestCompany.setAccepted(true);
						}
					}
				} else {
					httpStatus = HttpStatus.NOT_FOUND;
					responseBody.put("message", "invalid company uuid");
				}
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseBody.put("message", "invalid purchase request uuid");
			}

			this.purchaseRequestCompanyRepository.saveAll(allPurchaseRequestsSentToCompanies);
			httpStatus = HttpStatus.OK;
			responseBody.put("accepted-purchase-request-uuid", purchaseRequestToAccept.getUuid());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
	}

	@GetMapping(value = "/dealer/requests")
	public ResponseEntity<List<GetPurchaseRequestCompanyResponse>> getPurchaseRequests(
			@RequestHeader("authorization") String jwt) {
		Company company = companyRepository.findByUuid(jwt);
		try {
			List<GetPurchaseRequestCompanyResponse> response = new ArrayList<>();
			List<PurchaseRequestCompany> purchaseRequestCompanies = purchaseRequestCompanyRepository
					.getAllByCompanyId(company.getId());
			for (PurchaseRequestCompany purchaseRequestCompany : purchaseRequestCompanies) {
				GetPurchaseRequestCompanyResponse prbdr = new GetPurchaseRequestCompanyResponse();
				prbdr.setPurchase_request_uuid(purchaseRequestCompany.getPurchaseRequest().getUuid());
				prbdr.setDelivery_date(purchaseRequestCompany.getPurchaseRequest().getDeliveryDate());
				prbdr.setDelivery_price(purchaseRequestCompany.getPurchaseRequest().getDeliveryPrice());
				prbdr.setUuid(purchaseRequestCompany.getUuid());
				prbdr.setBrand_name(purchaseRequestCompany.getPurchaseRequest().getOffer().getVehicle().getBrandName());
				prbdr.setModel_name(purchaseRequestCompany.getPurchaseRequest().getOffer().getVehicle().getModelName());
				response.add(prbdr);
			}

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{purchase_request_uuid}")
	public ResponseEntity<PurchaseRequest> getSingle(@PathVariable(value = "purchase_request_uuid") String uuid) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		PurchaseRequest purchaseRequest = new PurchaseRequest();
		try {
			System.out.println(uuid);

			PurchaseRequestCompany purchaseRequestCompany = purchaseRequestCompanyRepository.getByUuid(uuid);
			long purchaseRequestId = purchaseRequestCompany.getPurchaseRequest().getId();
			System.out.println(purchaseRequestId);

			purchaseRequest = purchaseRequestRepository.getById(purchaseRequestId);
			System.out.println(purchaseRequest);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// return new ResponseEntity<PurchaseRequest>(purchaseRequest, httpStatus);

		return new ResponseEntity<PurchaseRequest>(purchaseRequest, httpStatus);

	}

	@GetMapping("/byoffer/{offer_uuid}")
	public ResponseEntity<List<PurchaseRequest>> getByOffer(@PathVariable(value = "offer_uuid") String offerUuid) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<PurchaseRequest> purchaseRequests = new ArrayList<PurchaseRequest>();

		try {
			Offer offer = this.offerRepository.findByUuid(offerUuid);
			purchaseRequests = this.purchaseRequestRepository.findListByOffer(offer);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<List<PurchaseRequest>>(purchaseRequests, httpStatus);
	}
}

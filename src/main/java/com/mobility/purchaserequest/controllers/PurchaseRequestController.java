package com.mobility.purchaserequest.controllers;

import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import com.mobility.purchaserequest.payloads.request.GetPurchaseRequestCompanyResponse;
import com.mobility.purchaserequest.payloads.response.PurchaseRequestResponse;
import com.mobility.purchaserequest.rabbitmq.PurchaseRequestSendService;
import com.mobility.purchaserequest.repositories.PurchaseRequestCompanyRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.PurchaseRequest;
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
	public ResponseEntity<Map<String, String>> create(@RequestBody CreatePurchaseRequestRequest request) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("message", "Internal server error.");

		try {
			// Fetch the offer. And check if it's valid.
			Offer offer = offerRepository.findByUuid(request.getOfferUuid());
			if (offer != null) {
				// Fetch all the companies the request will be created for.
				List<Company> companies = new ArrayList<Company>();
				for (String companyUuid : request.getCompanyUuids()) {
					Company company = companyRepository.findByUuid(companyUuid);
					// TODO else gedeelte maken als company id niet bestaat + transaction.
					if (company != null) {
						companies.add(company);
					}
				}

				if (companies.size() != 0) {
					// Create the purchase request
					PurchaseRequest purchaseRequest = new PurchaseRequest(
							offer,
							request.getDeliveryDate(),
							request.getDeliveryPrice());

					// Create a new entity for each company that receives the purchase request.
					List<PurchaseRequestCompany> purchaseRequestsCompanies = new ArrayList<PurchaseRequestCompany>();
					for (Company company : companies) {
						PurchaseRequestCompany purchaseRequestCompany = new PurchaseRequestCompany(
								company,
								null,
								purchaseRequest);
						purchaseRequestsCompanies.add(purchaseRequestCompany);
					}

					// Save the purchase request
					purchaseRequestRepository.save(purchaseRequest);
					// Save the purchaseRequestCompanies
					purchaseRequestCompanyRepository.saveAll(purchaseRequestsCompanies);
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

	@PostMapping("/{purchase_request_uuid}/accept")
	public ResponseEntity<Map<String, String>> acceptPurchaseRequest(
			@PathVariable(value = "purchase_request_uuid") String uuid,
			@RequestHeader("authorization") String jwt) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();

		String companyUuid = jwt;

		try {
			PurchaseRequestCompany purchaseRequestToAccept = purchaseRequestCompanyRepository.getByUuidAndCompanyUuid(
					uuid, companyUuid);

			if (purchaseRequestToAccept.getAccepted() != null) {
				httpStatus = HttpStatus.NOT_FOUND;
				return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
			}

			if (purchaseRequestToAccept != null && purchaseRequestToAccept.getUuid() != null) {
				purchaseRequestToAccept.setAccepted(true);

				purchaseRequestCompanyRepository.save(purchaseRequestToAccept);

			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseBody.put("message", "invalid purchase request uuid");
			}
			PurchaseRequestSendService.publishAcceptedPurchaseRequest(purchaseRequestToAccept);

			httpStatus = HttpStatus.OK;
			responseBody.put("accepted-purchase-request-uuid", purchaseRequestToAccept.getUuid());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
	}

	@PostMapping("/{purchase_request_uuid}/decline")
	public ResponseEntity<Map<String, String>> declinePurchaseRequest(
			@PathVariable(value = "purchase_request_uuid") String uuid,
			@RequestHeader("authorization") String jwt) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();

		String companyUuid = jwt;

		try {
			PurchaseRequestCompany purchaseRequestToAccept = purchaseRequestCompanyRepository.getByUuidAndCompanyUuid(
					uuid, companyUuid);

			// Stop if accepted is not NULL
			if (purchaseRequestToAccept.getAccepted() != null) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
			}

			if (purchaseRequestToAccept != null && purchaseRequestToAccept.getUuid() != null) {
				purchaseRequestToAccept.setAccepted(false);

				purchaseRequestCompanyRepository.save(purchaseRequestToAccept);
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				responseBody.put("message", "invalid purchase request uuid");
			}

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
		HttpStatus httpStatusCode;
		List<GetPurchaseRequestCompanyResponse> response = new ArrayList<>();
		String companyUuid = jwt;

		Company company = companyRepository.findByUuid(companyUuid);

		try {
			if (company != null) {
				List<PurchaseRequestCompany> purchaseRequestCompanies = purchaseRequestCompanyRepository
						.getAllByCompanyIdAndAcceptedIsNull(company.getId());
				response = GetPurchaseRequestCompanyResponse
						.convertPurchaseRequestCompanyList(purchaseRequestCompanies);
				httpStatusCode = HttpStatus.OK;
			} else {
				httpStatusCode = HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(response, httpStatusCode);
	}

	@GetMapping("/{purchase_request_uuid}")
	public ResponseEntity<PurchaseRequestResponse> getSingle(
			@PathVariable(value = "purchase_request_uuid") String uuid,
			@RequestHeader("authorization") String jwt) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		PurchaseRequestResponse purchaseRequestResponse = new PurchaseRequestResponse();

		String companyUuid = jwt;
		try {
			PurchaseRequestCompany purchaseRequestCompany = purchaseRequestCompanyRepository
					.getByUuidAndCompanyUuidAndAcceptedIsNull(uuid, companyUuid);

			if (purchaseRequestCompany == null) {

				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				long purchaseRequestId = purchaseRequestCompany.getPurchaseRequest().getId();

				PurchaseRequest purchaseRequest = new PurchaseRequest();
				purchaseRequest = purchaseRequestRepository.getById(purchaseRequestId);

				purchaseRequestResponse = new PurchaseRequestResponse(purchaseRequest);

				httpStatus = HttpStatus.OK;

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<PurchaseRequestResponse>(purchaseRequestResponse, httpStatus);

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

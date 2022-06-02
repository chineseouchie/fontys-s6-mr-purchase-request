package com.mobility.purchaserequest.controllers;

import com.mobility.purchaserequest.models.PurchaseRequestCompany;
import com.mobility.purchaserequest.payloads.request.AssignPurchaseRequestRequest;
import com.mobility.purchaserequest.payloads.response.GetPurchaseRequestCompanyResponse;
import com.mobility.purchaserequest.payloads.response.DealerResponse;
import com.mobility.purchaserequest.payloads.response.PurchaseRequestResponse;
import com.mobility.purchaserequest.rabbitmq.PurchaseRequestSendService;
import com.mobility.purchaserequest.models.repositories.PurchaseRequestCompanyRepository;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.models.Jwt;
import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.payloads.request.CreatePurchaseRequestRequest;
import com.mobility.purchaserequest.models.repositories.CompanyRepository;
import com.mobility.purchaserequest.models.repositories.OfferRepository;
import com.mobility.purchaserequest.models.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.utils.JwtParser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/v1/purchase-request")
@Transactional(rollbackOn = { SQLException.class })
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
	// Todo: Wijzig naar een PUT request (we updaten een bestaande entiteit)
	@PostMapping(path = "/create")
	public ResponseEntity<Map<String, String>> create(@RequestBody CreatePurchaseRequestRequest request) {
		System.out.println(request.getDeliveryPrice());

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
					System.out.println("Invalid company uuid");
					responseBody.put("message", "Invalid company uuid\'s.");
				}
			} else {
				System.out.println("Invalid offer uuid.");

				responseBody.put("message", "Invalid offer uuid.");
				httpStatus = HttpStatus.NOT_FOUND;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
	}

	// Todo: Wijzig naar een PUT request (we updaten een bestaande entiteit)
	@PostMapping("/{purchase_request_uuid}/accept")
	public ResponseEntity<Map<String, String>> acceptPurchaseRequest(
			@PathVariable(value = "purchase_request_uuid") String uuid,
			@RequestHeader("authorization") String jwt) throws JsonMappingException, JsonProcessingException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();

		// Todo: De companyUuid ophalen uit een echte jwt
		// Nadat de authenticatie implementatie gereed is.
		Jwt token = new JwtParser().ParseToken(jwt);
		System.out.println(token.getSub());
		String companyUuid = token.getSub();

		try {
			PurchaseRequestCompany purchaseRequestToAccept = purchaseRequestCompanyRepository.getByUuidAndCompanyUuid(
					uuid, companyUuid);

			if (purchaseRequestToAccept.getAccepted() != null) {
				httpStatus = HttpStatus.NOT_FOUND;
				return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
			}

			if (purchaseRequestToAccept != null && purchaseRequestToAccept.getUuid() != null) {
				purchaseRequestToAccept.setAccepted(true);

				// Vraag: Is het nodig dat de rest van de PurchaseRequestCompanies declined
				// worden wanneer er een geaccepteerd wordt? -Jip
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

	@PostMapping("/{purchase_request_uuid}/decline")
	public ResponseEntity<Map<String, String>> declinePurchaseRequest(
			@PathVariable(value = "purchase_request_uuid") String uuid,
			@RequestHeader("authorization") String jwt) throws JsonMappingException, JsonProcessingException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, String> responseBody = new HashMap<String, String>();

		// Todo: De companyUuid ophalen uit een echte jwt
		// Nadat de authenticatie implementatie gereed is.
		Jwt token = new JwtParser().ParseToken(jwt);
		System.out.println(token.getSub());
		String companyUuid = token.getSub();

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
			@RequestHeader("authorization") String jwt) throws JsonMappingException, JsonProcessingException {
		HttpStatus httpStatusCode;
		List<GetPurchaseRequestCompanyResponse> response = new ArrayList<>();

		// Todo: De companyUuid ophalen uit een echte jwt
		// Nadat de authenticatie implementatie gereed is.
		Jwt token = new JwtParser().ParseToken(jwt);
		System.out.println(token.getSub());
		String companyUuid = token.getSub();

		System.out.println(companyUuid);
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

	@GetMapping("/{purchase_request_company_uuid}")
	public ResponseEntity<PurchaseRequestResponse> getSingle(
			@PathVariable(value = "purchase_request_company_uuid") String uuid,
			@RequestHeader("authorization") String jwt) throws JsonMappingException, JsonProcessingException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		PurchaseRequestResponse purchaseRequestResponse = new PurchaseRequestResponse();

		Jwt token = new JwtParser().ParseToken(jwt);
		System.out.println(token.getSub());
		String companyUuid = token.getSub();
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

	@GetMapping("/dealers")
	public ResponseEntity<List<DealerResponse>> getDealers() {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<DealerResponse> responses = new ArrayList<>();
		try {
			List<Company> companies = this.companyRepository.findAll();
			responses = DealerResponse.convertCompanyToResponse(companies);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(responses, httpStatus);
	}

	@GetMapping
	public ResponseEntity<List<PurchaseRequestResponse>> getPurchaseRequests() {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<PurchaseRequestResponse> purchaseRequests = new ArrayList<>();

		try {
			List<PurchaseRequest> list = this.purchaseRequestRepository.findAll();
			purchaseRequests = PurchaseRequestResponse.convertList(list);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(purchaseRequests, httpStatus);
	}

	@GetMapping("{purchase_request_uuid}/companies")
	public ResponseEntity<List<PurchaseRequestCompany>> getPurchaseRequestCompanies(@PathVariable(value = "purchase_request_uuid") String uuid) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		List<PurchaseRequestCompany> purchaseRequestsCompanies = new ArrayList<>();
		try {
			purchaseRequestsCompanies = this.purchaseRequestCompanyRepository.findAllByPurchaseRequestUuidAndAcceptedIsTrue(uuid);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<>(purchaseRequestsCompanies, httpStatus);
	}

	@PutMapping("assign")
	public ResponseEntity<String> assignPurchaseRequest(@RequestBody AssignPurchaseRequestRequest request) throws IOException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String message = "";

		try {
			PurchaseRequestCompany purchaseRequestToAccept = purchaseRequestCompanyRepository.getByUuidAndCompanyUuid(
					request.getPurchase_request_uuid(), request.getPurchase_request_company_uuid());

			if(purchaseRequestToAccept != null) {
				PurchaseRequestSendService.publishAcceptedPurchaseRequest(purchaseRequestToAccept);
				purchaseRequestRepository.deleteByUuid(request.getPurchase_request_uuid());

				message = "the dealer received the purchase request";
				httpStatus = HttpStatus.OK;
			} else {
				message = "purchase request or dealer not found";
				httpStatus = HttpStatus.NOT_FOUND;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			message = "something went wrong...";
		}

		return new ResponseEntity<>(message, httpStatus);
	}
}

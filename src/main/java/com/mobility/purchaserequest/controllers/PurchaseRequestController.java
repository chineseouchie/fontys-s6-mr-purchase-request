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

    public PurchaseRequestController(PurchaseRequestRepository purchaseRequestRepository, OfferRepository offerRepository, CompanyRepository companyRepository, PurchaseRequestCompanyRepository purchaseRequestCompanyRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.offerRepository = offerRepository;
        this.companyRepository = companyRepository;
        this.purchaseRequestCompanyRepository = purchaseRequestCompanyRepository;
    }

    @GetMapping(value = "/dealer/requests")
    public ResponseEntity<List<GetPurchaseRequestCompanyResponse>> getPurchaseRequests(@RequestHeader ("authorization") String jwt) {
        System.out.println(jwt);
        HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
         Company company = companyRepository.findByUuid(jwt);

        try {
            List<GetPurchaseRequestCompanyResponse> response = new ArrayList<>();
            List<PurchaseRequestCompany> purchaseRequestCompanies = purchaseRequestCompanyRepository.getAllByCompanyId(company.getId());

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

            return new ResponseEntity<>(response, httpStatusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        try {
//            List<PurchaseRequestCompany> list = PurchaseRequestRepository.findAll();
//
//            if(list != null) {
//                response = GetVehicleResponse.convertVehicleList(list);
//
//                // Sends vehicle to message bus (RabbitMQ)
//                list.forEach(vehicle -> {
//                    RabbitMQService.publishCreateVehicle(vehicle);
//                });
//
//                httpStatusCode = HttpStatus.OK
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//

    }


//    @PostMapping(path = "/create")
//    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody CreatePurchaseRequestRequest request) {
//        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        Map<String, String> responseBody = new HashMap<String, String>();
//
//        try {
//            Offer offer = this.offerRepository.findByUuid(request.getOfferUuid());
//            Company company = this.companyRepository.findByUuid(request.getCompanyUuid());
//
//            PurchaseRequest purchaseRequest = new PurchaseRequest(offer, company, request.getDeliveryDate(), request.getDeliveryPrice());
//            this.purchaseRequestRepository.save(purchaseRequest);
//
//            httpStatus = HttpStatus.OK;
//            responseBody.put("purchase-request-uuid", purchaseRequest.getUuid());
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
//    }

//    @PostMapping(path = "/accept")
//    public ResponseEntity<Map<String, String>> acceptPurchaseRequest(@RequestBody String purchase_request_uuid) {
//        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        Map<String, String> responseBody = new HashMap<String, String>();
//
//        try {
//            PurchaseRequest purchaseRequestToAccept = this.purchaseRequestRepository.findByUuid(purchase_request_uuid);
//            List<PurchaseRequest> purchaseRequestsToDecline = this.purchaseRequestRepository.findListByOffer(purchaseRequestToAccept.getOffer());
//
//            purchaseRequestToAccept.setAccepted(true);
//            for(PurchaseRequest purchaseRequest : purchaseRequestsToDecline) {
//                purchaseRequest.setAccepted(false);
//                this.purchaseRequestRepository.save(purchaseRequest);
//            }
//
//            httpStatus = HttpStatus.OK;
//            responseBody.put("accepted-purchase-request-uuid", purchaseRequestToAccept.getUuid());
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
//    }
//
//    @PostMapping(path = "/decline")
//    public ResponseEntity<Map<String, String>> declinePurchaseRequest(@RequestBody String purchase_request_uuid) {
//        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//        Map<String, String> responseBody = new HashMap<String, String>();
//
//        try {
//            PurchaseRequest purchaseRequestToDecline = this.purchaseRequestRepository.findByUuid(purchase_request_uuid);
//            purchaseRequestToDecline.setAccepted(false);
//            this.purchaseRequestRepository.save(purchaseRequestToDecline);
//
//            httpStatus = HttpStatus.OK;
//            responseBody.put("declined-purchase-request-uuid", purchaseRequestToDecline.getUuid());
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        return new ResponseEntity<Map<String, String>>(responseBody, httpStatus);
//    }

    @GetMapping("/single/{purchase_request_uuid}")
    public ResponseEntity<PurchaseRequest> getSingle(@PathVariable(value="purchase_request_uuid") String uuid) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        PurchaseRequest purchaseRequest = null;

        try {
            purchaseRequest = this.purchaseRequestRepository.findByUuid(uuid);
            httpStatus = HttpStatus.FOUND;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<PurchaseRequest>(purchaseRequest, httpStatus);
    }

    @GetMapping("/byoffer/{offer_uuid}")
    public ResponseEntity<List<PurchaseRequest>> getByOffer(@PathVariable(value="offer_uuid") String offerUuid){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        List<PurchaseRequest> purchaseRequests = new ArrayList<PurchaseRequest>();

        try {
            Offer offer = this.offerRepository.findByUuid(offerUuid);
            purchaseRequests = this.purchaseRequestRepository.findListByOffer(offer);
            httpStatus = HttpStatus.OK;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<List<PurchaseRequest>>(purchaseRequests, httpStatus);
    }
}
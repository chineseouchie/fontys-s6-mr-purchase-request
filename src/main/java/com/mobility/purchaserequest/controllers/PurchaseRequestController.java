package com.mobility.purchaserequest.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.payloads.request.CreatePurchaseRequestRequest;
import com.mobility.purchaserequest.payloads.response.CreatePurchaseRequestResponse;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/purchase-request")
public class PurchaseRequestController {
    /*
    private PurchaseRequestRepository purchaseRequestRepository;
    private VehicleRepository vehicleRepository;

    public PurchaseRequestController(PurchaseRequestRepository purchaseRequestRepository, VehicleRepository vehicleRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("")
	public String index() {
		return "PurchaseRequestController works.";
	};


    @PostMapping(path = "/create")
	@ResponseBody
    public ResponseEntity<CreatePurchaseRequestResponse> create(@Valid @RequestBody CreatePurchaseRequestRequest request) {
        HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
        CreatePurchaseRequestResponse response = new CreatePurchaseRequestResponse();

        //Check if the vehicle already exists in the service's database.
        //Use the existing vehicle if it exists. Or create and store a new one.
        try {
            Vehicle vehicleToPurchase = this.vehicleRepository.findByUuid(request.getVehicleUuid());
            if(vehicleToPurchase == null) {
                vehicleToPurchase = new Vehicle(
                    request.getVehicleUuid(), 
                    request.getModelName(), 
                    request.getBrandName(), 
                    request.getColor(),
                    request.getImageUrl()
                );
                this.vehicleRepository.save(vehicleToPurchase);
                //Save the stored vehicle in the response body
            }

            //Create a new purchase request. And store it in the database
            PurchaseRequest purchaseRequest = new PurchaseRequest(
                UUID.randomUUID().toString(),
                request.getOfferUuid(),
                vehicleToPurchase,
                request.getDeliveryDate(),
                request.getDeliveryPrice()
            );
            purchaseRequest = this.purchaseRequestRepository.save(purchaseRequest);

            //Create the response
            httpStatusCode = HttpStatus.CREATED;
            response.setPurchaseRequestUuid(purchaseRequest.getUuid());
            response.setVehicle(vehicleToPurchase);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return new ResponseEntity<CreatePurchaseRequestResponse>(response, httpStatusCode);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<PurchaseRequest> getByUuid(@PathVariable(value="uuid") String uuid) {
        HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
        PurchaseRequest purchaseRequest = null;

        try {
            purchaseRequest = this.purchaseRequestRepository.findByUuid(uuid);
            if(purchaseRequest != null) {
                httpStatusCode = HttpStatus.FOUND;
            } else {
                httpStatusCode = HttpStatus.NOT_FOUND;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return new ResponseEntity<PurchaseRequest>(purchaseRequest, httpStatusCode);
    }
    */
}
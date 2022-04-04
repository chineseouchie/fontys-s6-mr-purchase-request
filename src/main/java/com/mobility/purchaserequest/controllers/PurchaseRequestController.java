package com.mobility.purchaserequest.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import com.google.gson.Gson;
import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.payloads.CreatePurchaseRequestRequest;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/purchase/request")
public class PurchaseRequestController {
    private PurchaseRequestRepository purchaseRequestRepository;
    private VehicleRepository vehicleRepository;

    public PurchaseRequestController(PurchaseRequestRepository purchaseRequestRepository, VehicleRepository vehicleRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @PostMapping(path = "/create")
	@ResponseBody
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody CreatePurchaseRequestRequest request) {
        HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
        Map<String, String> responseBody = new HashMap<>();
        Gson gson = new Gson();

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
            responseBody.put("purchaseRequestUuid", purchaseRequest.getUuid());
            responseBody.put("vehicle", gson.toJson(vehicleToPurchase));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return new ResponseEntity<Map<String, String>>(responseBody, httpStatusCode);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Map<String, String>> getByUuid(@PathVariable(value="uuid") String uuid) {
        HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
        Map<String, String> responseBody = new HashMap<>();
        Gson gson = new Gson();

        try {
            PurchaseRequest purchaseRequest = this.purchaseRequestRepository.findByUuid(uuid);
            if(purchaseRequest != null) {
                httpStatusCode = HttpStatus.FOUND;
                responseBody.put("purchaseRequest", gson.toJson(responseBody));
            } else {
                responseBody.put("purchaseRequest", null);
                httpStatusCode = HttpStatus.NOT_FOUND;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        return new ResponseEntity<Map<String, String>>(responseBody, httpStatusCode);
    }
}
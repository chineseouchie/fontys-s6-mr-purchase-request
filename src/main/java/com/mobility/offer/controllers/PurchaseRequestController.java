package com.mobility.offer.controllers;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import com.mobility.offer.models.PurchaseRequest;
import com.mobility.offer.models.Vehicle;
import com.mobility.offer.payloads.CreatePurchaseRequestRequest;
import com.mobility.offer.repositories.PurchaseRequestRepository;
import com.mobility.offer.repositories.VehicleRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
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
    public ResponseEntity<Null> create(@Valid @RequestBody CreatePurchaseRequestRequest request) {
        HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;

        try {
            //Fetch the vehicle to purchase by uuid
            Vehicle vehicleToPurchase = this.vehicleRepository.getByUuid(request.getVehicleUuid());
            //If the vehicle is found. Create the purchase request
            //If not. Throw a error.
            if(vehicleToPurchase != null) {
                
            } else {
                
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
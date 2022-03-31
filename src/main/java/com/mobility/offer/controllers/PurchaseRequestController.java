package com.mobility.offer.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mobility.offer.repositories.PurchaseRequestRepository;
import com.mobility.offer.repositories.VehicleRepository;

import org.springframework.context.annotation.Primary;
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
}
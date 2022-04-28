package com.mobility.purchaserequest;

import com.mobility.purchaserequest.rabbitmq.OfferReceiver;

import com.mobility.purchaserequest.rabbitmq.VehicleReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PurchaseRequestApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(PurchaseRequestApplication.class, args);
		VehicleReceiver.init();
		OfferReceiver.init();
		System.out.println("Purchase Request service ready.");
	}
}

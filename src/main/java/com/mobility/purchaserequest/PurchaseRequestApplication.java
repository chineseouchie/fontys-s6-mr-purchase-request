package com.mobility.purchaserequest;

import com.mobility.purchaserequest.rabbitmq.OfferReceiver;
import com.mobility.purchaserequest.rabbitmq.UserReceiver;
import com.mobility.purchaserequest.rabbitmq.VehicleReceiver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class PurchaseRequestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseRequestApplication.class, args);
		System.out.println("Purchase Request service ready. Port 8087");
	}

	@Override
	public void run(String... args) throws Exception {

		VehicleReceiver.init();
		OfferReceiver.init();
		UserReceiver.init();
	}
}

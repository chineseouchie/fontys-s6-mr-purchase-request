package com.mobility.purchaserequest;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.rabbitmq.OfferReceiver;

import com.mobility.purchaserequest.rabbitmq.VehicleReceiver;
import com.mobility.purchaserequest.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PurchaseRequestApplication implements CommandLineRunner {
	@Autowired
	private CompanyRepository companyRepository;

	public static void main(String[] args) {
		SpringApplication.run(PurchaseRequestApplication.class, args);
		System.out.println("Purchase Request service ready. Port 8087");
	}

	@Override
	public void run(String... args) throws Exception {
		VehicleReceiver.init();
		OfferReceiver.init();

		Company bmw_dealer = new Company("company_ABC", "BMW Dealer");
		Company audi_dealer = new Company("company_DEF", "Audi Dealer");
		Company opel_dealer = new Company("company_GHI", "Opel Dealer");
		Company tesla_dealer = new Company("company_JKL", "Tesla Dealer");

		companyRepository.save(bmw_dealer);
		companyRepository.save(audi_dealer);
		companyRepository.save(opel_dealer);
		companyRepository.save(tesla_dealer);
	}
}

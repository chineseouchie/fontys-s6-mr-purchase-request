package com.mobility.purchaserequest;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.repositories.CompanyRepository;
import com.mobility.purchaserequest.repositories.OfferRepository;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.util.UUID.randomUUID;
import static org.aspectj.runtime.internal.Conversions.longValue;

@SpringBootApplication
public class PurchaseRequestApplication implements CommandLineRunner {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;


	public static void main(String[] args) {
		SpringApplication.run(PurchaseRequestApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {



		Vehicle serie_3 = new Vehicle(1, "ABC", "3 Serie", "BMW", "Red", "https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle s6 = new Vehicle(2, "DEF", "S6", "Audi", "Blue", "https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle vectra = new Vehicle(3, "GHI", "Vectra", "Opel", "red", "https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle model_s = new Vehicle(4, "JKL", "Model S", "Tesla", "red", "https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle m5 = new Vehicle(5, "MNO", "M5", "BMW", "red", "https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");

		vehicleRepository.save(serie_3);
		vehicleRepository.save(s6);
		vehicleRepository.save(vectra);
		vehicleRepository.save(model_s);
		vehicleRepository.save(m5);

		Company bmw_dealer = new Company(longValue(1), "ABC", "BMW Dealer");
		Company audi_dealer = new Company(longValue(2), "DEF", "Audi Dealer");
		Company opel_dealer = new Company(longValue(3), "GHI", "Opel Dealer");
		Company tesla_dealer = new Company(longValue(4), "JKL", "Tesla Dealer");

		companyRepository.save(bmw_dealer);
		companyRepository.save(audi_dealer);
		companyRepository.save(opel_dealer);
		companyRepository.save(tesla_dealer);

		Offer offerABC = new Offer(longValue(1), "offerABC", "XYZ", m5, 1649404689);
		Offer offerDEF = new Offer(longValue(2), "offerDEF", "UVW", s6, 1649404689);
		Offer offerGHI = new Offer(longValue(3), "offerGHI", "RST", vectra, 1649404689);
		Offer offerJKL = new Offer(longValue(4), "offerJKL", "TKJ", model_s, 1649404689 );

		offerRepository.save(offerABC);
		offerRepository.save(offerDEF);
		offerRepository.save(offerGHI);
		offerRepository.save(offerJKL);

		PurchaseRequest prABC = new PurchaseRequest(longValue(1), "prABC", offerABC,  1650404689, BigInteger.valueOf(126690));
		PurchaseRequest prDEF = new PurchaseRequest(longValue(2), "prDEF", offerDEF,  1650494689, BigInteger.valueOf(126690));
		PurchaseRequest prGHI = new PurchaseRequest(longValue(3), "prGHI", offerGHI,  1650504689, BigInteger.valueOf(126690));
		PurchaseRequest prJKL = new PurchaseRequest(longValue(4), "prJKL", offerJKL, 1651409689, BigInteger.valueOf(126690));

		purchaseRequestRepository.save(prABC);
		purchaseRequestRepository.save(prDEF);
		purchaseRequestRepository.save(prGHI);
		purchaseRequestRepository.save(prJKL);

		List<Company> companiesPr1 = new ArrayList<>();
		companiesPr1.add(audi_dealer);
		companiesPr1.add(bmw_dealer);
		companiesPr1.add(opel_dealer);

		List<Company> companiesPr2 = new ArrayList<>();
		companiesPr2.add(audi_dealer);
		companiesPr2.add(bmw_dealer);
		companiesPr2.add(tesla_dealer);

		List<Company> companiesPr3 = new ArrayList<>();
		companiesPr3.add(tesla_dealer);
		companiesPr3.add(bmw_dealer);
		companiesPr3.add(opel_dealer);

		List<Company> companiesPr4 = new ArrayList<>();
		companiesPr4.add(audi_dealer);
		companiesPr4.add(tesla_dealer);
		companiesPr4.add(opel_dealer);

		prABC.setCompanies(companiesPr1);

	}

}
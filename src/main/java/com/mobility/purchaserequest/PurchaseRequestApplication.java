package com.mobility.purchaserequest;

import com.mobility.purchaserequest.models.*;
import com.mobility.purchaserequest.rabbitmq.OfferReceiveService;
import com.mobility.purchaserequest.repositories.CompanyRepository;
import com.mobility.purchaserequest.repositories.OfferRepository;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.repositories.PurchaseRequestCompanyRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.math.BigInteger;

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

	@Autowired
	private PurchaseRequestCompanyRepository purchaseRequestCompanyRepository;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PurchaseRequestApplication.class, args);
		OfferReceiveService.startReceiving();
		System.out.println("Purchase Request service ready.");
	}

	@Override
	public void run(String... args) throws Exception {
		Vehicle serie_3 = new Vehicle(1, "vehicle_ABC", "3 Serie", "BMW", "Red",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle s6 = new Vehicle(2, "vehicle_DEF", "S6", "Audi", "Blue",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle vectra = new Vehicle(3, "vehicle_GHI", "Vectra", "Opel", "red",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle model_s = new Vehicle(4, "vehicle_JKL", "Model S", "Tesla", "red",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle m5 = new Vehicle(5, "vehicle_MNO", "vehicle_M5", "BMW", "red",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");

		vehicleRepository.save(serie_3);
		vehicleRepository.save(s6);
		vehicleRepository.save(vectra);
		vehicleRepository.save(model_s);
		vehicleRepository.save(m5);

		Company bmw_dealer = new Company("company_ABC", "BMW Dealer");
		Company audi_dealer = new Company("company_DEF", "Audi Dealer");
		Company opel_dealer = new Company("company_GHI", "Opel Dealer");
		Company tesla_dealer = new Company("company_JKL", "Tesla Dealer");

		companyRepository.save(bmw_dealer);
		companyRepository.save(audi_dealer);
		companyRepository.save(opel_dealer);
		companyRepository.save(tesla_dealer);

		Offer offerABC = new Offer("offerABC", "user_XYZ", m5, 1649404689);
		Offer offerDEF = new Offer("offerDEF", "user_UVW", s6, 1649404689);
		Offer offerGHI = new Offer("offerGHI", "user_RST", vectra, 1649404689);
		Offer offerJKL = new Offer("offerJKL", "user_TKJ", model_s, 1649404689);

		offerRepository.save(offerABC);
		offerRepository.save(offerDEF);
		offerRepository.save(offerGHI);
		offerRepository.save(offerJKL);

		PurchaseRequest prABC = new PurchaseRequest("prABC", offerABC, 1650404689, BigInteger.valueOf(126690));
		PurchaseRequest prDEF = new PurchaseRequest("prDEF", offerDEF, 1650494689, BigInteger.valueOf(126690));
		PurchaseRequest prGHI = new PurchaseRequest("prGHI", offerGHI, 1650504689, BigInteger.valueOf(126690));
		PurchaseRequest prJKL = new PurchaseRequest("prJKL", offerJKL, 1651409689, BigInteger.valueOf(126690));

		purchaseRequestRepository.save(prABC);
		purchaseRequestRepository.save(prDEF);
		purchaseRequestRepository.save(prGHI);
		purchaseRequestRepository.save(prJKL);

		PurchaseRequestCompany ppr1 = new PurchaseRequestCompany("pprABC", bmw_dealer, prABC);
		PurchaseRequestCompany ppr2 = new PurchaseRequestCompany("pprDEF", audi_dealer, prABC);
		PurchaseRequestCompany ppr3 = new PurchaseRequestCompany("pprGHI", opel_dealer, prABC);
		PurchaseRequestCompany ppr4 = new PurchaseRequestCompany("pprJKL", tesla_dealer, prABC);
		PurchaseRequestCompany ppr5 = new PurchaseRequestCompany("pprMNO", bmw_dealer, prDEF);
		PurchaseRequestCompany ppr6 = new PurchaseRequestCompany("pprPQR", audi_dealer, prDEF);
		PurchaseRequestCompany ppr7 = new PurchaseRequestCompany("pprSTU", tesla_dealer, prDEF);

		purchaseRequestCompanyRepository.save(ppr1);
		purchaseRequestCompanyRepository.save(ppr2);
		purchaseRequestCompanyRepository.save(ppr3);
		purchaseRequestCompanyRepository.save(ppr4);
		purchaseRequestCompanyRepository.save(ppr5);
		purchaseRequestCompanyRepository.save(ppr6);
		purchaseRequestCompanyRepository.save(ppr7);

	}

}

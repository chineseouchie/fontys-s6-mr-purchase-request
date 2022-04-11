package com.mobility.purchaserequest;

import com.mobility.purchaserequest.models.*;
import com.mobility.purchaserequest.repositories.CompanyRepository;
import com.mobility.purchaserequest.repositories.OfferRepository;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.repositories.PerformedPurchaseRequestsRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	private PerformedPurchaseRequestsRepository performedPurchaseRequestsRepository;

	public static void main(String[] args) {
		SpringApplication.run(PurchaseRequestApplication.class, args);
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

		Company bmw_dealer = new Company("offer_ABC", "BMW Dealer");
		Company audi_dealer = new Company("offer_DEF", "Audi Dealer");
		Company opel_dealer = new Company("offer_GHI", "Opel Dealer");
		Company tesla_dealer = new Company("offer_JKL", "Tesla Dealer");

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

		PerformedPurchaseRequest ppr1 = new PerformedPurchaseRequest("pprABC", bmw_dealer, prABC);
		PerformedPurchaseRequest ppr2 = new PerformedPurchaseRequest("pprDEF", audi_dealer, prABC);
		PerformedPurchaseRequest ppr3 = new PerformedPurchaseRequest("pprGHI", opel_dealer, prABC);
		PerformedPurchaseRequest ppr4 = new PerformedPurchaseRequest("pprJKL", tesla_dealer, prABC);
		PerformedPurchaseRequest ppr5 = new PerformedPurchaseRequest("pprMNO", bmw_dealer, prDEF);
		PerformedPurchaseRequest ppr6 = new PerformedPurchaseRequest("pprPQR", audi_dealer, prDEF);
		PerformedPurchaseRequest ppr7 = new PerformedPurchaseRequest("pprSTU", tesla_dealer, prDEF);

		performedPurchaseRequestsRepository.save(ppr1);
		performedPurchaseRequestsRepository.save(ppr2);
		performedPurchaseRequestsRepository.save(ppr3);
		performedPurchaseRequestsRepository.save(ppr4);
		performedPurchaseRequestsRepository.save(ppr5);
		performedPurchaseRequestsRepository.save(ppr6);
		performedPurchaseRequestsRepository.save(ppr7);

	}

}

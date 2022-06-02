package com.mobility.purchaserequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mobility.purchaserequest.controllers.PurchaseRequestController;
import com.mobility.purchaserequest.models.*;
import com.mobility.purchaserequest.models.repositories.*;
import com.mobility.purchaserequest.payloads.response.GetPurchaseRequestCompanyResponse;
import com.mobility.purchaserequest.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.longValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PurchaseRequestControllerDealerSideTest {

	@Mock
	private PurchaseRequestRepository purchaseRequestRepository;

	@Mock
	private PurchaseRequestCompanyRepository purchaseRequestCompanyRepository;

	@Mock
	private VehicleRepository vehicleRepository;

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private CompanyRepository companyRepository;

	@InjectMocks
	private PurchaseRequestController purchaseRequestController;

	private List<PurchaseRequestCompany> mockPurchaseRequestCompanies;
	private List<PurchaseRequestCompany> mockPurchaseRequestCompaniesBMW;

	private Company bmw_dealer;
	private Company audi_dealer;
	private Company opel_dealer;
	private Company tesla_dealer;

	private PurchaseRequestCompany ppr1;
	private PurchaseRequestCompany ppr5;

	@BeforeEach
	private void init() {

		Vehicle s6 = new Vehicle(2, "vehicle_DEF", "S6", "Audi",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");
		Vehicle m5 = new Vehicle(5, "vehicle_MNO", "vehicle_M5", "BMW",
				"https://res.cloudinary.com/directlease/image/fetch/t_transp,f_png,dpr_auto/https://images.directlease.nl/jato_nl/Photo400/BMW/SERIES%203/2022/4SA%20M3_315.JPG");

		bmw_dealer = new Company(longValue(1), "company_ABC", "BMW Dealer");
		audi_dealer = new Company("company_DEF", "Audi Dealer");
		opel_dealer = new Company("company_GHI", "Opel Dealer");
		tesla_dealer = new Company("company_JKL", "Tesla Dealer");

		Offer offerABC = new Offer("offerABC", "user_XYZ", m5, 1649404689, "Blue");
		Offer offerDEF = new Offer("offerDEF", "user_UVW", s6, 1649404689, "red");
		
		PurchaseRequest prABC = new PurchaseRequest("prABC", offerABC, 1650404689, BigDecimal.valueOf(126690));
		PurchaseRequest prDEF = new PurchaseRequest("prDEF", offerDEF, 1650494689, BigDecimal.valueOf(126690));
		
		ppr1 = new PurchaseRequestCompany("pprABC", bmw_dealer, prABC);
		PurchaseRequestCompany ppr2 = new PurchaseRequestCompany("pprDEF", audi_dealer, prABC);
		PurchaseRequestCompany ppr3 = new PurchaseRequestCompany("pprGHI", opel_dealer, prABC);
		PurchaseRequestCompany ppr4 = new PurchaseRequestCompany("pprJKL", tesla_dealer, prABC);
		ppr5 = new PurchaseRequestCompany("pprMNO", bmw_dealer, prDEF);
		PurchaseRequestCompany ppr6 = new PurchaseRequestCompany("pprPQR", audi_dealer, prDEF);
		PurchaseRequestCompany ppr7 = new PurchaseRequestCompany("pprSTU", tesla_dealer, prDEF);

		mockPurchaseRequestCompanies = new ArrayList<>();
		mockPurchaseRequestCompanies.add(ppr1);
		mockPurchaseRequestCompanies.add(ppr2);
		mockPurchaseRequestCompanies.add(ppr3);
		mockPurchaseRequestCompanies.add(ppr4);
		mockPurchaseRequestCompanies.add(ppr5);
		mockPurchaseRequestCompanies.add(ppr6);
		mockPurchaseRequestCompanies.add(ppr7);

		mockPurchaseRequestCompaniesBMW = new ArrayList<>();
		mockPurchaseRequestCompaniesBMW.add(ppr1);
		mockPurchaseRequestCompaniesBMW.add(ppr5);
	}

	@Test
	void getAllPurchaseRequestsForCompanyTest() throws JsonMappingException, JsonProcessingException {
		given(companyRepository.findByUuid(bmw_dealer.getUuid())).willReturn(bmw_dealer);
		given(purchaseRequestCompanyRepository.getAllByCompanyId(bmw_dealer.getId()))
				.willReturn(mockPurchaseRequestCompaniesBMW);

		ResponseEntity<List<GetPurchaseRequestCompanyResponse>> purchaseRequestCompanyResponse = purchaseRequestController
				.getPurchaseRequests("company_ABC");

		assertNotNull(purchaseRequestCompanyResponse);
		assertTrue(purchaseRequestCompanyResponse.hasBody());

		assertEquals(purchaseRequestCompanyResponse.getBody().get(0), new GetPurchaseRequestCompanyResponse(ppr1));
		assertEquals(purchaseRequestCompanyResponse.getBody().get(1), new GetPurchaseRequestCompanyResponse(ppr5));

		assertEquals(purchaseRequestCompanyResponse.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void getAllPurchaseRequestsForCompanyWithNonExistingIdTest() throws JsonMappingException, JsonProcessingException {
		ResponseEntity<List<GetPurchaseRequestCompanyResponse>> purchaseRequestCompanyResponse = purchaseRequestController
				.getPurchaseRequests("company_ABC_NotFound");

		assertNotNull(purchaseRequestCompanyResponse);
		assertTrue(purchaseRequestCompanyResponse.hasBody());
		assertTrue(purchaseRequestCompanyResponse.getBody().size() == 0);

		assertEquals(purchaseRequestCompanyResponse.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}

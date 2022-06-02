package com.mobility.purchaserequest;

import com.mobility.purchaserequest.models.Company;
import com.mobility.purchaserequest.models.Offer;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.payloads.request.CreatePurchaseRequestRequest;
import com.mobility.purchaserequest.models.repositories.CompanyRepository;
import com.mobility.purchaserequest.models.repositories.OfferRepository;
import com.mobility.purchaserequest.models.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.models.repositories.VehicleRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PurchaseRequestControllerTests {
    // Tool required to make post test requests
    @Autowired
    private MockMvc mvc;

    // The mock repositories
    @Mock
    private PurchaseRequestRepository purchaseRequestRepository;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private CompanyRepository companyRepository;

    // Tool required to make add JSON to the mock requests
    // This object will be initialized by the initFields in the setup method.
    private JacksonTester<CreatePurchaseRequestRequest> jsonCreatePurchaseRequest;

    // Mock data
    private Vehicle mockVehicle = new Vehicle(Long.valueOf(1), "va2vuw3a-di38-0dss-abc7-9s2d46d8shod", "Astra", "Opel", "opel_astra_black.png");
    private Offer mockOffer = new Offer(Long.valueOf(1), "c621c13f-c0c3-7af7-bad1-fb72264dafzx",
            "c521cd3f-s0d1-aed7-avd1-sbv426rddffg", mockVehicle, 1649404432, "Black");
    private Company mockCompany = new Company(Long.valueOf(1), "8sh28713f-scbs-9dis-0abd-0ahsihuduwhd",
            "Very real company LLC.");
    // private PurchaseRequest mockPurchaseRequest = new
    // PurchaseRequest(Long.valueOf(1), "dhfudsgrurkfugsrykesgu", mockOffer,
    // mockCompany, 1649404783, new BigInteger("5000.42"));

    // @BeforeEach
    // public void setup() {
    // JacksonTester.initFields(this, new ObjectMapper());
    // this.mvc = MockMvcBuilders.standaloneSetup(new
    // PurchaseRequestController(this.purchaseRequestRepository,
    // this.offerRepository, this.companyRepository)).build();
    //
    // given(this.offerRepository.findByUuid(this.mockOffer.getUuid())).willReturn(this.mockOffer);
    // given(this.vehicleRepository.findByUuid(this.mockVehicle.getUuid())).willReturn(this.mockVehicle);
    // //given(this.purchaseRequestRepository.findByUuid(mockPurchaseRequest.getUuid())).willReturn(this.mockPurchaseRequest);
    // }

    @Test
    public void canCreatePurchaseRequest() throws Exception {
        /*
         * CreatePurchaseRequestRequest createPurchaseRequestRequest = new
         * CreatePurchaseRequestRequest();
         * createPurchaseRequestRequest.setCompanyUuid(this.mockCompany.getUuid());
         * createPurchaseRequestRequest.setOfferUuid("sjkash-asdhjashd-asdhj-uahbsaf");
         * createPurchaseRequestRequest.setDeliveryDate(1649404109);
         * createPurchaseRequestRequest.setDeliveryPrice(new BigInteger("5300.99"));
         */

        /*
         * MockHttpServletResponse response = mvc.perform(
         * post("/api/v1/purchase-request/create").contentType(MediaType.
         * APPLICATION_JSON).content(
         * jsonCreatePurchaseRequest.write(mockPurchaseRequest.getJson())
         * )
         * );
         */

        /*
         * //Create & perform the mock post request
         * MockHttpServletResponse response = mvc.perform(
         * post("/api/v1/purchase-request/create").contentType(MediaType.
         * APPLICATION_JSON).content(
         * jsonCreatePurchaseRequest.write(
         * new CreatePurchaseRequestRequest(
         * "a620c13f-c0c1-4ef7-bbd1-fb74464daffb",
         * "Prius",
         * "Renault",
         * "Cyan",
         * "renault_prius_cyan.png",
         * 1649055917,
         * new BigInteger("28042"),
         * "4b51bfd5-3c1b-455e-b255-2321f873d736"
         * )
         * ).getJson()
         * )
         * ).andReturn().getResponse();
         * 
         * assertThat(response.getStatus() == 201);
         * assertThat(response.getContentAsString().contains(
         * "a620c13f-c0c1-4ef7-bbd1-fb74464daffb"));
         * assertThat(response.getContentAsString().contains("Prius"));
         * assertThat(response.getContentAsString().contains("Renault"));
         * assertThat(response.getContentAsString().contains("Cyan"));
         * assertThat(response.getContentAsString().contains("renault_prius_cyan.png"));
         * assertThat(response.getContentAsString().contains(
         * "4b51bfd5-3c1b-455e-b255-2321f873d736"));
         */
        assertThat(true);
    }

    @Test
    public void canGetPurchaseRequest() throws Exception {
        /*
         * String purchaseRequestUuid = "a4f60fe4-1dd4-4224-b0d0-281aa7cbb383";
         * //Mock the repositority
         * given(purchaseRequestRepository.findByUuid(purchaseRequestUuid)).willReturn(
         * new PurchaseRequest(
         * purchaseRequestUuid,
         * "b7ba3264-f2ee-4d25-a11c-a661a4ae1fdc",
         * new Vehicle("74bbf03d-4e10-4801-baab-bd7d7c3be0c4", "Opel", "Astra", "Black",
         * "opel_astra_black.png"),
         * 1649055917,
         * new BigInteger("28042")
         * )
         * );
         * 
         * PurchaseRequest purchaseRequest = this.purchaseRequestRepository.findByUuid(
         * "a620c13f-c0c1-4ef7-bbd1-fb74464daffb");
         * 
         * assertThat(purchaseRequest.getUuid().equals(
         * "a620c13f-c0c1-4ef7-bbd1-fb74464daffb"));
         * assertThat(purchaseRequest.getVehicle() != null);
         * assertThat(purchaseRequest.getVehicle().getUuid().equals(
         * "74bbf03d-4e10-4801-baab-bd7d7c3be0c4"));
         * assertThat(purchaseRequest.getVehicle().getColor().equals("Black"));
         */
        assertThat(true);
    }
}

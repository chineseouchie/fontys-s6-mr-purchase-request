package com.mobility.purchaserequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobility.purchaserequest.controllers.PurchaseRequestController;
import com.mobility.purchaserequest.models.PurchaseRequest;
import com.mobility.purchaserequest.models.Vehicle;
import com.mobility.purchaserequest.repositories.PurchaseRequestRepository;
import com.mobility.purchaserequest.repositories.VehicleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigInteger;

@ExtendWith(MockitoExtension.class)
public class PurchaseRequestControllerTests {
    private MockMvc mvc;
    
    @Mock
    private PurchaseRequestRepository purchaseRequestRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    // This object will be  initialized by the initFields in the setup method.
    private JacksonTester<PurchaseRequest> jsonPurchaseRequest;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(new PurchaseRequestController(this.purchaseRequestRepository, this.vehicleRepository)).build();
    }

    public void testCreatePurchaseRequestCorrectData() throws Exception {
        MockHttpServletResponse response = mvc.perform(
            post("/api/v1/purchase-request/create").contentType(MediaType.APPLICATION_JSON).content(
                jsonPurchaseRequest.write(
                    new PurchaseRequest(
                        "a620c13f-c0c1-4ef7-bbd1-fb74464daffb",
                        "675a7273-0872-42a9-85d8-4bc26a3e4b03",
                        new Vehicle(
                            "f68dd708-84ab-4b57-bd32-dc471e67ed50",
                            "Opel",
                            "Astra",
                            "Black",
                            "opel_astra_black.png"
                        ),
                        1649055917,
                        new BigInteger("28042.00")
                    )
                )
            )
        );
    }
}

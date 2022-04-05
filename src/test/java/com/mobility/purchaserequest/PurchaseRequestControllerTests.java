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


}

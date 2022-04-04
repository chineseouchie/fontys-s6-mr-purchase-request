package com.mobility.purchaserequest.repositories;

import com.mobility.purchaserequest.models.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;

//import com.mobility.offer.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByUuid(String uuid);
}

//Ja, deze meuk gaat binnenkort weg. -Jip
/*
public VehicleRepository() {
    this.mockDataStore = new ArrayList<Vehicle>();
    this.mockDataStore.add(new Vehicle(1, UUID.randomUUID().toString(), "Roma", "Ferrari", "Rood", "https://media.autoweek.nl/m/fj1yvh0bpa67_800.jpg"));
    this.mockDataStore.add(new Vehicle(2, UUID.randomUUID().toString(), "Arkana", "Renault", "Rood", "https://media.autoweek.nl/m/djlyukabaxue_800.jpg"));
    this.mockDataStore.add(new Vehicle(3, UUID.randomUUID().toString(), "Edge", "Ford", "Wit", "https://media.autoweek.nl/m/s3oyavobny2z_600.jpg"));
    this.mockDataStore.add(new Vehicle(4, UUID.randomUUID().toString(), "Mini", "Rover", "Zwart", "https://media.autoweek.nl/m/m1by0cmbo7ym_600.jpg"));
    this.mockDataStore.add(new Vehicle(5, UUID.randomUUID().toString(), "I20", "Hyundai", "Zwart", "https://media.autoweek.nl/m/spnyym9bhjpw_600.jpg"));
}
*/
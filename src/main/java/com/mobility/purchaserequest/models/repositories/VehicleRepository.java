package com.mobility.purchaserequest.models.repositories;

import com.mobility.purchaserequest.models.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByUuid(String uuid);
}

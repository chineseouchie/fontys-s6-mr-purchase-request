package com.mobility.purchaserequest.repositories;

import java.util.List;

import com.mobility.purchaserequest.models.Dealer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    List<Dealer> findByCompanyUuid(String companyUuid);
    List<Dealer> findByCompanyName(String companyName);
}

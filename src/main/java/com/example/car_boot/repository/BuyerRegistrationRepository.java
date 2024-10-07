package com.example.car_boot.repository;

import com.example.car_boot.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRegistrationRepository extends JpaRepository<Buyer, Long> {

}

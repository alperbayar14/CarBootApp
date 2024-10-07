package com.example.car_boot.service;

import com.example.car_boot.entity.Buyer;
import com.example.car_boot.repository.BuyerRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerService {

    private final BuyerRegistrationRepository buyerRepository;

    @Autowired
    public BuyerService(BuyerRegistrationRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    // Servis metodları burada tanımlanır
}

package com.example.car_boot.controller;

import com.example.car_boot.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyers")
public class BuyerRegistrationController {

    private final BuyerService buyerService;

    @Autowired
    public BuyerRegistrationController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    // Controller endpoint'leri burada tanımlanır
}

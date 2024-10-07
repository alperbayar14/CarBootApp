package com.example.car_boot.service;


import com.example.car_boot.entity.Seller;
import com.example.car_boot.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Seller getSellerById(Long id) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        return optionalSeller.orElse(null);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller updateSeller(Long id, Seller sellerDetails) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            Seller existingSeller = optionalSeller.get();
            existingSeller.setEmail(sellerDetails.getEmail());
            existingSeller.setPassword(sellerDetails.getPassword());
            existingSeller.setVehicleRegistrationNumber(sellerDetails.getVehicleRegistrationNumber());
            existingSeller.setSelectedEventId(sellerDetails.getSelectedEventId());
            return sellerRepository.save(existingSeller);
        } else {
            return null;
        }
    }

    public boolean deleteSeller(Long id) {
        Optional<Seller> optionalSeller = sellerRepository.findById(id);
        if (optionalSeller.isPresent()) {
            sellerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}

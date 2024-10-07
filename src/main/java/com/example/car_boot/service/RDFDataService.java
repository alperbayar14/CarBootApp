package com.example.car_boot.service;

import com.example.car_boot.repository.RDFDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RDFDataService {
    @Autowired
    private RDFDataRepository rdfDataRepository;

    public String performQueryAndProcessResults() {
        return rdfDataRepository.executeAllQueries();
    }
}

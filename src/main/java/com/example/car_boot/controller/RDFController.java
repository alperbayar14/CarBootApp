package com.example.car_boot.controller;

import com.example.car_boot.service.SPARQLQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rdf")
public class RDFController {
    @Autowired
    private SPARQLQueryService sparqlQueryService;

    @GetMapping("/query")
    public ResponseEntity<String> queryTripleStore() {
        try {
            sparqlQueryService.executeAllQueries();
            return ResponseEntity.ok("Queries executed successfully. Check server logs for results.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error executing queries: " + e.getMessage());
        }
    }
}

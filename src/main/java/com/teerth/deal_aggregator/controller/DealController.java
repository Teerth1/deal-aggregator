package com.teerth.deal_aggregator.controller;

import java.util.List;
import com.teerth.deal_aggregator.model.Deal;
import com.teerth.deal_aggregator.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/api/deals")
public class DealController {
    
    @Autowired
    private DealService dealService;

    @GetMapping
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = dealService.getAllDeals();

        return ResponseEntity.ok(deals);
    }

    //Get api/deals/{id} - get only one deal from id
    @GetMapping("/{id}")
    public ResponseEntity<Deal> getDealById(@PathVariable Long id) {
        Optional<Deal> dealOptional = dealService.getDealById(id);

        if (dealOptional.isPresent()) {
            Deal deal = dealOptional.get();
            return ResponseEntity.ok(deal); //HTTP 200 With the Deal
        } else {
            return ResponseEntity.notFound().build(); //HTTP 404
        }
    }
    //Post is going to create a new deal
    @PostMapping
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {
        Deal savedDeal = dealService.saveDeal(deal);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeal);
    }

    // PUT /api/deals/{id} - Update an existing deal
    @PutMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal updatedDeal) {
        Optional<Deal> existingDealOptional = dealService.getDealById(id);
        
        // Check if deal exists
        if (existingDealOptional.isPresent()) {
            // Deal exists, so update it
            updatedDeal.setId(id);  // Make sure we update the correct deal
            Deal savedDeal = dealService.saveDeal(updatedDeal);
            return ResponseEntity.ok(savedDeal);  // HTTP 200 with updated deal
        } else {
            // Deal doesn't exist
            return ResponseEntity.notFound().build();  // HTTP 404
        }
    }

    // DELETE /api/deals/{id} - Delete a deal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeal(@PathVariable Long id) {
        Optional<Deal> dealOptional = dealService.getDealById(id);
        
        // Check if deal exists
        if (dealOptional.isPresent()) {
            // Deal exists, delete it
            dealService.deleteDeal(id);
            return ResponseEntity.noContent().build();  // HTTP 204 (success, no content)
        } else {
            // Deal doesn't exist
            return ResponseEntity.notFound().build();  // HTTP 404
        }
    }

    // GET /api/deals/count - Get total count
    @GetMapping("/count")
    public ResponseEntity<Long> countDeals() {
        long count = dealService.countDeals();
        return ResponseEntity.ok(count);
    }

}

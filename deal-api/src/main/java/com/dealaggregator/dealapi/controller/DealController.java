package com.dealaggregator.dealapi.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dealaggregator.dealapi.entity.Deal;
import com.dealaggregator.dealapi.service.DealScraperService;
import com.dealaggregator.dealapi.service.DealService;


@RestController
@RequestMapping("/api/deals")
public class DealController {
    // Controller methods would go here

    private final DealService dealService;
    private final DealScraperService dealScraperService;

    
    public DealController(DealService dealService, DealScraperService dealScrapperService){  
        this.dealScraperService = dealScrapperService;
        this.dealService = dealService;
    }

    @GetMapping
    public List<Deal> getAllDeals() {
        return dealService.getAllDeals();
    }

    @GetMapping("/{id}")
    public Deal getDealById(@PathVariable Long id) {
        return dealService.getDealById(id);
    }

    @GetMapping("/scrape-test")
    public String testScrape() {
        dealScraperService.scrapeReddit();
        return "Check console for results";
    }

    @PostMapping
    public Deal createDeal(@RequestBody Deal deal) {
        return dealService.createDeal(deal);
    }

    @PutMapping("/{id}")
    public Deal updateDeal(@PathVariable Long id, @RequestBody Deal dealDetails) {
        return dealService.updateDeal(id, dealDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
    }


    @GetMapping("/category/{category}")
    public List<Deal> getDealsByCategory(@PathVariable String category) {
        return dealService.getDealsByCategory(category);
    }

    @GetMapping("/search")
    public List<Deal> searchDeals(@RequestParam String keyword) {
        return dealService.searchDeals(keyword);
    }


}   
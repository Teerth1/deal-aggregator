package com.teerth.deal_aggregator.service;

import com.teerth.deal_aggregator.model.Deal;
import com.teerth.deal_aggregator.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DealService {
    @Autowired
    private DealRepository dealRepository;

    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    public Optional<Deal> getDealById(Long id) {
        return dealRepository.findById(id);
    }

    public Deal saveDeal(Deal deal) {
        return dealRepository.save(deal);
    }

    public void deleteDeal(Long id) {
        dealRepository.deleteById(id);
    }

    public long countDeals() {
        return dealRepository.count();
    }
}

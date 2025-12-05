

package com.dealaggregator.dealapi.service;
import org.springframework.stereotype.Service;
import com.dealaggregator.dealapi.repository.DealRepository;
import com.dealaggregator.dealapi.entity.Deal;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class DealService {
    
    private final DealRepository dealRepository;

    public DealService(DealRepository repository) {
        this.dealRepository = repository;
    }

    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    public Deal getDealById(Long id) {
        return dealRepository.findById(id).orElseThrow(() -> new RuntimeException("Deal not found with id: " + id));
    }

    public Deal createDeal(Deal deal) {
        return dealRepository.save(deal);
    }

    public Deal updateDeal(Long id, Deal dealDetails) {
        Deal existingDeal = getDealById(id);

        existingDeal.setTitle(dealDetails.getTitle());
        existingDeal.setPrice(dealDetails.getPrice());
        existingDeal.setOriginalPrice(dealDetails.getOriginalPrice());
        existingDeal.setDiscountPercentage(dealDetails.getDiscountPercentage());
        existingDeal.setVendor(dealDetails.getVendor());
        existingDeal.setDealUrl(dealDetails.getDealUrl());
        existingDeal.setCategory(dealDetails.getCategory());
        existingDeal.setDealType(dealDetails.getDealType());
        existingDeal.setDescription(dealDetails.getDescription());
        return dealRepository.save(existingDeal);
    }
    public void deleteDeal(Long id) {
        dealRepository.deleteById(id);
    }
    public List<Deal> getDealsByCategory(String category) {
        return dealRepository.findByCategory(category);
    }

    public List<Deal> getDealsByVendor(String vendor) {
        return dealRepository.findByVendor(vendor);
    }
    public List<Deal> getDealsCheaperThan(BigDecimal price) {
        return dealRepository.findByPriceLessThan(price);
    }
    public List<Deal> searchDeals(String keyword) {
        return dealRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
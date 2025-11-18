package com.teerth.deal_aggregator.repository;
import com.teerth.deal_aggregator.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DealRepository extends JpaRepository<Deal,Long> {
    
}

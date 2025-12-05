package com.dealaggregator.dealapi.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dealaggregator.dealapi.entity.Deal;


@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByCategory(String category);

    List<Deal> findByVendor(String vendor);

    List<Deal> findByTitleContainingIgnoreCase(String keyword);

    List<Deal> findByPriceLessThan(BigDecimal price);

}